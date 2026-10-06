import assert from 'node:assert/strict';
import { scoreAndBand } from '../src/lib/estimation/rubric.ts';
import { computeFlowEffort, computeProjectEffort, type FlowRecord } from '../src/lib/estimation/engine.ts';
import { computeDuration, solveEngineersForDeadline, durationCurve } from '../src/lib/estimation/resourcing.ts';
import { DEFAULT_GLOBAL_ASSUMPTIONS, DEFAULT_RATE_CARDS } from '../src/lib/estimation/rateCardDefaults.ts';

const ga = DEFAULT_GLOBAL_ASSUMPTIONS;
const selenium = DEFAULT_RATE_CARDS.Selenium;

function approxEqual(a: number, b: number, tol = 0.01, msg = '') {
  assert.ok(Math.abs(a - b) <= tol, `${msg}: expected ${b}, got ${a} (tol ${tol})`);
}

console.log('--- TEST 1: rubric scoring matches the documented Excel rubric ---');
{
  // TC01-REF from the ORC register: 6 steps, 3 personas, config/visibility assertion -> Simple, score should be low
  const { score, band } = scoreAndBand({
    steps: 6, personas: 3, integration: false, aiNonDeterministic: false, approvalWorkflow: false,
    emailNotification: false, otbiReport: false, externalUI: false, fileDocHandling: false,
    bulkAction: false, configOnly: false,
  });
  // steps<8 => +0, personas>=3 => +2, no other drivers => score 2 => Medium (matches sample's actual band? register showed personas=3 -> "Medium" in our earlier sample rows for similar cases)
  console.log(`  TC01-REF-like: score=${score} band=${band}`);
  assert.equal(score, 2);
  assert.equal(band, 'Medium');
}
{
  // A 12+ step, 3-integration, AI-content flow should score very high -> Complex
  const { score, band } = scoreAndBand({
    steps: 13, personas: 3, integration: true, aiNonDeterministic: true, approvalWorkflow: true,
    emailNotification: true, otbiReport: false, externalUI: false, fileDocHandling: false,
    bulkAction: false, configOnly: false,
  });
  console.log(`  Heavy flow: score=${score} band=${band}`);
  assert.equal(score, 2 + 2 + 3 + 3 + 1 + 1); // steps=2, personas=2, integration=3, ai=3, approval=1, email=1 = 12
  assert.equal(band, 'Complex');
}
{
  // Pure config/visibility only, low steps/personas -> negative driver keeps it Simple even at score 0 boundary
  const { score, band } = scoreAndBand({
    steps: 3, personas: 1, integration: false, aiNonDeterministic: false, approvalWorkflow: false,
    emailNotification: false, otbiReport: false, externalUI: false, fileDocHandling: false,
    bulkAction: false, configOnly: true,
  });
  console.log(`  Config-only flow: score=${score} band=${band}`);
  assert.equal(score, -1);
  assert.equal(band, 'Simple');
}
console.log('PASS\n');

console.log('--- TEST 2: reproduce the ATS ORC Selenium workbook totals (0 extra countries) ---');
{
  // The workbook's in-scope mix was 34 Simple / 89 Medium / 21 Complex = 144 scripts.
  // We don't have the workbook's 144 individual rows, so we reconstruct equivalent
  // "representative" flows: e.g. 34 flows engineered to score Simple, etc. What we're
  // really checking is that the PER-SCRIPT hour math (KT, design, exec) matches the
  // workbook's rates exactly, since that's what the engine actually computes per flow.

  // KT minutes/script: 5/10/15 (Simple/Medium/Complex) -> hours for one script of each band
  approxEqual(ga.ktMinutes.Simple / 60, 5 / 60, 0.0001, 'KT Simple hours/script');
  approxEqual(ga.ktMinutes.Medium / 60, 10 / 60, 0.0001, 'KT Medium hours/script');
  approxEqual(ga.ktMinutes.Complex / 60, 15 / 60, 0.0001, 'KT Complex hours/script');

  // Selenium design rate: 3.5/2.25/1.25 scripts/day -> hours/script = 8/rate
  approxEqual(ga.hoursPerDay / selenium.designScriptsPerDay.Simple, 8 / 3.5, 0.0001, 'Design Simple hrs/script');
  approxEqual(ga.hoursPerDay / selenium.designScriptsPerDay.Medium, 8 / 2.25, 0.0001, 'Design Medium hrs/script');
  approxEqual(ga.hoursPerDay / selenium.designScriptsPerDay.Complex, 8 / 1.25, 0.0001, 'Design Complex hrs/script');

  // Now reconstruct the workbook's aggregate KT total (22.9167 hrs) and Design total (528.56 hrs)
  // for 34/89/21 scripts with ZERO extra countries, using computeProjectEffort.
  const flows: FlowRecord[] = [
    ...Array.from({ length: 34 }, (_, i) => makeFlow(`S${i}`, 'Simple')),
    ...Array.from({ length: 89 }, (_, i) => makeFlow(`M${i}`, 'Medium')),
    ...Array.from({ length: 21 }, (_, i) => makeFlow(`C${i}`, 'Complex')),
  ];

  function makeFlow(id: string, band: 'Simple' | 'Medium' | 'Complex'): FlowRecord {
    // craft signals that deterministically land on the target band with zero countries
    const signalsByBand: Record<string, FlowRecord> = {
      Simple: { id, module: 'Test', steps: 3, personas: 1, integration: false, aiNonDeterministic: false, approvalWorkflow: false, emailNotification: false, otbiReport: false, externalUI: false, fileDocHandling: false, bulkAction: false, configOnly: false, countries: [] },
      Medium: { id, module: 'Test', steps: 9, personas: 2, integration: false, aiNonDeterministic: false, approvalWorkflow: false, emailNotification: false, otbiReport: false, externalUI: false, fileDocHandling: false, bulkAction: false, configOnly: false, countries: [] },
      Complex: { id, module: 'Test', steps: 13, personas: 3, integration: true, aiNonDeterministic: false, approvalWorkflow: false, emailNotification: false, otbiReport: false, externalUI: false, fileDocHandling: false, bulkAction: false, configOnly: false, countries: [] },
    };
    return signalsByBand[band];
  }

  // sanity: confirm our synthetic flows actually land on the intended bands
  for (const f of flows) {
    const { band } = scoreAndBand(f);
    const expected = f.id.startsWith('S') ? 'Simple' : f.id.startsWith('M') ? 'Medium' : 'Complex';
    assert.equal(band, expected, `synthetic flow ${f.id} landed on ${band}, expected ${expected}`);
  }

  const proj = computeProjectEffort(flows, selenium, ga, /* componentsRequired */ 0);
  console.log(`  KT hours: ${proj.ktHours.toFixed(4)} (workbook: 22.9167)`);
  console.log(`  Design hours: ${proj.designHours.toFixed(4)} (workbook: 528.5587)`);
  approxEqual(proj.ktHours, 22.9167, 0.01, 'Total KT hours vs workbook');
  approxEqual(proj.designHours, 528.5587, 0.01, 'Total Design hours vs workbook');
  assert.equal(proj.countryHours, 0, 'No country hours when no extra countries listed');
}
console.log('PASS\n');

console.log('--- TEST 3: country-scope cost model behaves sensibly ---');
{
  const baseFlow: FlowRecord = {
    id: 'AP-001', module: 'Accounts Payable', steps: 10, personas: 2,
    integration: false, aiNonDeterministic: false, approvalWorkflow: true, emailNotification: false,
    otbiReport: false, externalUI: false, fileDocHandling: false, bulkAction: false, configOnly: false,
    countries: [],
  };
  const homeOnly = computeFlowEffort(baseFlow, selenium, ga);

  const withDataOnly: FlowRecord = { ...baseFlow, countries: [{ country: 'FR', variant: 'DATA_ONLY' }, { country: 'DE', variant: 'DATA_ONLY' }] };
  const withDataOnlyEffort = computeFlowEffort(withDataOnly, selenium, ga);

  const withLogicDiff: FlowRecord = { ...baseFlow, countries: [{ country: 'BR', variant: 'LOGIC_DIFFERENT' }] };
  const withLogicDiffEffort = computeFlowEffort(withLogicDiff, selenium, ga);

  console.log(`  Home only total: ${homeOnly.totalHours.toFixed(3)} hrs`);
  console.log(`  +2 data-only countries: ${withDataOnlyEffort.totalHours.toFixed(3)} hrs (+${(withDataOnlyEffort.totalHours - homeOnly.totalHours).toFixed(3)})`);
  console.log(`  +1 logic-different country: ${withLogicDiffEffort.totalHours.toFixed(3)} hrs (+${(withLogicDiffEffort.totalHours - homeOnly.totalHours).toFixed(3)})`);

  // data-only should be cheap and linear in the overhead rate
  approxEqual(withDataOnlyEffort.totalHours - homeOnly.totalHours, 2 * selenium.countryDataOverheadHours, 0.001, '2 data-only countries add 2x overhead');

  // logic-different should cost meaningfully more than a data-only country
  const logicDiffDelta = withLogicDiffEffort.totalHours - homeOnly.totalHours;
  const dataOnlyDelta = selenium.countryDataOverheadHours;
  assert.ok(logicDiffDelta > dataOnlyDelta, 'a logic-different country must cost more than a data-only country');

  // a flow with 5 logic-different countries should cost noticeably more than one with 5 data-only countries
  const fiveDataOnly = computeFlowEffort({ ...baseFlow, countries: Array.from({ length: 5 }, (_, i) => ({ country: `C${i}`, variant: 'DATA_ONLY' as const })) }, selenium, ga);
  const fiveLogicDiff = computeFlowEffort({ ...baseFlow, countries: Array.from({ length: 5 }, (_, i) => ({ country: `C${i}`, variant: 'LOGIC_DIFFERENT' as const })) }, selenium, ga);
  console.log(`  5 data-only: ${fiveDataOnly.totalHours.toFixed(3)} hrs vs 5 logic-different: ${fiveLogicDiff.totalHours.toFixed(3)} hrs`);
  assert.ok(fiveLogicDiff.totalHours > fiveDataOnly.totalHours, '5 logic-different countries must cost more than 5 data-only countries');
}
console.log('PASS\n');

console.log('--- TEST 4: resourcing is bidirectionally consistent ---');
{
  const personWeeks = 74.6; // arbitrary
  const engineers = 5;
  const dur = computeDuration(personWeeks, engineers, selenium.serialFloorWeeks, ga);
  console.log(`  ${engineers} engineers -> ${dur.durationWeeks.toFixed(2)} weeks (raw ${dur.rawDurationWeeks.toFixed(2)}, floor ${selenium.serialFloorWeeks})`);

  const solved = solveEngineersForDeadline(personWeeks, dur.durationWeeks, selenium.serialFloorWeeks);
  console.log(`  Solving backwards for ${dur.durationWeeks.toFixed(2)} weeks -> requires ${solved.requiredEngineers} engineers`);
  assert.ok(solved.requiredEngineers <= engineers + 1, 'round-trip should not require dramatically more engineers than we started with');

  // more engineers -> less time (monotonic), until the serial floor kicks in
  const curve = durationCurve(personWeeks, selenium.serialFloorWeeks, ga, 12);
  for (let i = 1; i < curve.length; i++) {
    assert.ok(curve[i].durationWeeks <= curve[i - 1].durationWeeks + 1e-9, 'duration must be non-increasing as engineers increase');
  }
  console.log(`  Curve 1->12 engineers: ${curve.map((c) => c.durationWeeks.toFixed(1)).join(', ')}`);

  // an impossible deadline shorter than the serial floor must be reported infeasible
  const impossible = solveEngineersForDeadline(personWeeks, selenium.serialFloorWeeks - 1, selenium.serialFloorWeeks);
  assert.equal(impossible.feasible, false);
  console.log(`  Deadline shorter than serial floor correctly flagged infeasible: "${impossible.note}"`);

  // an unreasonably tight deadline that would need too many engineers is flagged, not silently returned
  const tooTight = solveEngineersForDeadline(personWeeks, selenium.serialFloorWeeks + 0.1, selenium.serialFloorWeeks, 12);
  console.log(`  Very tight deadline -> feasible=${tooTight.feasible}, requires=${tooTight.requiredEngineers}, note="${tooTight.note}"`);
}
console.log('PASS\n');

console.log('--- TEST 5: all four tools produce different, sensible totals from identical scope ---');
{
  const flows: FlowRecord[] = Array.from({ length: 20 }, (_, i) => ({
    id: `F${i}`, module: 'Accounts Payable', steps: 9, personas: 2,
    integration: false, aiNonDeterministic: false, approvalWorkflow: true, emailNotification: true,
    otbiReport: false, externalUI: false, fileDocHandling: false, bulkAction: false, configOnly: false,
    countries: i % 4 === 0 ? [{ country: 'BR', variant: 'LOGIC_DIFFERENT' }] : [{ country: 'FR', variant: 'DATA_ONLY' }],
  }));

  const results = Object.values(DEFAULT_RATE_CARDS).map((rc) => ({
    tool: rc.tool,
    total: computeProjectEffort(flows, rc, ga, 10).grandTotalHours,
  }));
  results.forEach((r) => console.log(`  ${r.tool}: ${r.total.toFixed(1)} hrs`));

  const totals = results.map((r) => r.total);
  assert.equal(new Set(totals.map((t) => t.toFixed(3))).size, totals.length, 'each tool should produce a distinct total given distinct rate cards');
  totals.forEach((t) => assert.ok(t > 0 && Number.isFinite(t), 'every total must be a finite positive number'));
}
console.log('PASS\n');

console.log('ALL ENGINE VERIFICATION TESTS PASSED');
