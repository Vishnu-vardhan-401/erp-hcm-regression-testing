import assert from 'node:assert/strict';
import { proposeCalibration } from '../src/lib/estimation/calibration.ts';
import { DEFAULT_RATE_CARDS } from '../src/lib/estimation/rateCardDefaults.ts';

const selenium = DEFAULT_RATE_CARDS.Selenium;

console.log('--- TEST 1: a small sample nudges gently, a large sample converges more ---');
{
  // Design consistently ran at 150% of estimate (took longer than planned)
  const smallSample = Array.from({ length: 2 }, () => ({ activity: 'Design', estimatedHours: 100, actualHours: 150 }));
  const largeSample = Array.from({ length: 30 }, () => ({ activity: 'Design', estimatedHours: 100, actualHours: 150 }));

  const proposalSmall = proposeCalibration('Selenium', selenium, smallSample);
  const proposalLarge = proposeCalibration('Selenium', selenium, largeSample);

  const originalComplexRate = selenium.designScriptsPerDay.Complex;
  const smallComplexRate = proposalSmall.proposedRateCard.designScriptsPerDay!.Complex;
  const largeComplexRate = proposalLarge.proposedRateCard.designScriptsPerDay!.Complex;

  console.log(`  original Complex scripts/day: ${originalComplexRate}`);
  console.log(`  after small sample (n=2): ${smallComplexRate.toFixed(3)}`);
  console.log(`  after large sample (n=30): ${largeComplexRate.toFixed(3)}`);

  // both should move DOWN (took longer than planned -> fewer scripts/day going forward)
  assert.ok(smallComplexRate < originalComplexRate, 'small sample should still nudge the rate down');
  assert.ok(largeComplexRate < smallComplexRate, 'large sample should move further than small sample');
  assert.equal(proposalSmall.confidence, 'low');
  assert.equal(proposalLarge.confidence, 'high');
}
console.log('PASS\n');

console.log('--- TEST 2: actuals matching the estimate exactly propose no change ---');
{
  const perfectSample = Array.from({ length: 10 }, () => ({ activity: 'Execution', estimatedHours: 50, actualHours: 50 }));
  const proposal = proposeCalibration('Selenium', selenium, perfectSample);
  const rate = proposal.proposedRateCard.execScriptsPerDay!;
  console.log(`  proposed exec rates when actual==estimate: Simple=${rate.Simple.toFixed(4)} (orig ${selenium.execScriptsPerDay.Simple})`);
  assert.ok(Math.abs(rate.Simple - selenium.execScriptsPerDay.Simple) < 1e-9, 'no drift when actual matches estimate exactly');
}
console.log('PASS\n');

console.log('--- TEST 3: proposal never mutates the current rate card in place ---');
{
  const before = JSON.stringify(selenium);
  proposeCalibration('Selenium', selenium, [{ activity: 'Setup', estimatedHours: 32, actualHours: 60 }]);
  const after = JSON.stringify(selenium);
  assert.equal(before, after, 'calibration must be a pure function - the input rate card must not be mutated');
}
console.log('PASS\n');

console.log('--- TEST 4: mixed activities each map to the right rate-card field, others untouched ---');
{
  const pairs = [
    { activity: 'Setup', estimatedHours: 32, actualHours: 40 },
    { activity: 'PM', estimatedHours: 20, actualHours: 15 },
  ];
  const proposal = proposeCalibration('Selenium', selenium, pairs);
  assert.ok(proposal.proposedRateCard.setupHours! > selenium.setupHours, 'setup ran over -> setupHours should rise');
  assert.ok(proposal.proposedRateCard.pmPct! < selenium.pmPct, 'PM ran under -> pmPct should fall');
  assert.equal(proposal.proposedRateCard.hoursPerComponent, undefined, 'untouched activities should not appear in the proposal at all');
  console.log(`  ${proposal.summary}`);
}
console.log('PASS\n');

console.log('ALL CALIBRATION VERIFICATION TESTS PASSED');
