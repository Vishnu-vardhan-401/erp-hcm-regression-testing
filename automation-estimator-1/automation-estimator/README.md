# Automation Estimator

AI-driven test automation effort estimation. Drop in requirement docs or a test-case
register, pick an automation tool (Selenium / UiPath / Playwright / Tosca), and get an
hours-and-duration estimate that accounts for complexity, multi-country scope, and team
size - with a live "how much time do I save with more people" planner and a "when do I
need to start to hit this deadline" solver.

This exists because spreadsheet-based estimation (the previous iteration of this
project) works but doesn't scale: every new tool needs a new workbook, every new
project means re-deriving complexity by hand, and nothing ever learns from how the
last project's actuals compared to its estimate. This app keeps the same underlying
math (it's a direct, verified port - see below) but makes the scope-extraction step
AI-driven, makes the tool choice a live comparison instead of a separate file, and adds
a real staffing planner and a calibration loop.

## The concrete scenario this was built around

> "I'm running Accounts Payable end-to-end in Playwright, ~200 test cases across
> different country scopes. I need the estimate before the project starts, with
> respect to a deadline, and I want to see how the timeline changes if I add or
> remove resources."

Every piece of this app maps directly to that sentence:
- **~200 test cases** → the Test Flow Register, either AI-extracted from your
  requirement docs or entered manually.
- **different country scopes** → each flow can list additional countries, tagged as
  either `DATA_ONLY` (same logic, different test data - cheap) or `LOGIC_DIFFERENT`
  (genuinely different fields/approval rules - expensive). See "The country-scope cost
  model" below.
- **in Playwright** → the tool selector; the exact same scope produces a different
  estimate for Selenium/UiPath/Tosca, computed from the same flows.
- **before the project starts, with respect to a deadline** → the Estimate tab's
  deadline solver: give it a date, it tells you how many engineers you need, or tells
  you honestly that the deadline isn't achievable no matter how many people you add.
- **how the timeline changes with more/fewer resources** → the resource-planner slider
  and chart on the same tab, computed instantly (no server round-trip) because the
  resourcing math is a pure function that ships to the browser.

## Architecture

```
                                    ┌─────────────────────────┐
  Upload (docx/pdf/xlsx/csv)  ───▶  │  Parser (extract text)  │
                                    └─────────────┬────────────┘
                                                   ▼
                                    ┌─────────────────────────┐
                                    │  Claude (extraction)     │  → structured flows,
                                    │  src/lib/ai/extractFlows │    validated with Zod
                                    └─────────────┬────────────┘
                                                   ▼
                          ┌────────────────────────────────────────┐
                          │  Test Flow Register (Flow rows in DB)   │
                          │  rubric.ts scores complexity, deriving  │
                          │  a review flag for low-confidence/      │
                          │  AI-content/logic-different-country     │
                          │  flows so a human signs off before they │
                          │  count toward a trusted number          │
                          └─────────────────┬────────────────────────┘
                                            ▼
                          ┌────────────────────────────────────────┐
                          │  Tool Rate Cards (one per tool, org-    │
                          │  scoped, versioned, editable)           │
                          └─────────────────┬────────────────────────┘
                                            ▼
                          ┌────────────────────────────────────────┐
                          │  engine.ts computeProjectEffort()       │
                          │  → hours by activity, incl. country cost│
                          └─────────────────┬────────────────────────┘
                                            ▼
                          ┌────────────────────────────────────────┐
                          │  resourcing.ts (pure, ships to browser) │
                          │  team size ⇄ duration, either direction │
                          └────────────────────────────────────────┘
                                            ▼
                          ┌────────────────────────────────────────┐
                          │  ActualLog (logged during delivery)     │
                          │  → calibration.ts proposes rate changes │
                          │  → human approves before it's live      │
                          └────────────────────────────────────────┘
```

**Stack:** Next.js 15 (App Router) + TypeScript, PostgreSQL via Prisma, Auth.js v5
(credentials), Anthropic API for extraction/recommendation, Tailwind, Recharts.

## Quick start (Docker - fastest path)

```bash
cp .env.example .env
# edit .env: set ANTHROPIC_API_KEY at minimum. AUTH_SECRET too if you're not just
# kicking the tires locally (generate one with: openssl rand -base64 32)

docker compose up --build
```

Then, in a second terminal, run migrations and seed the initial admin user + rate cards:

```bash
docker compose exec app npx prisma migrate deploy
docker compose exec app npm run db:seed
```

Visit `http://localhost:3000/login`. Seed prints the admin email/password to the
console (defaults are in `.env.example` - **change the password after first login in a
real deployment**, there's no UI for that yet, use Prisma Studio: `npm run db:studio`).

## Quick start (local, no Docker)

```bash
npm install
cp .env.example .env
# point DATABASE_URL at your own Postgres, set ANTHROPIC_API_KEY, AUTH_SECRET

npx prisma migrate dev --name init
npm run db:seed
npm run dev
```

## Verifying the estimation math before you trust it

The estimation engine is pure, dependency-free TypeScript, which means two things:

1. It can run directly under Node with **zero installed dependencies** - useful for
   checking this repo hasn't been tampered with, or just understanding the math without
   spinning up the whole app:
   ```bash
   npm run verify:engine        # reproduces the source Selenium workbook's exact
                                 # numbers (22.9167 KT hours, 528.5587 design hours)
                                 # as a regression check, then exercises the country
                                 # cost model and resourcing solver
   npm run verify:calibration   # confirms calibration nudges gently on small samples,
                                 # converges further on large ones, and never mutates
                                 # its input
   ```
2. The same logic also has a proper Vitest suite for CI (`npm test`), covering the same
   ground in the idiomatic form.

If you change anything in `src/lib/estimation/`, run `npm run verify` before trusting
the output - it's fast and catches real regressions (it caught two bugs of my own while
building this: a synthetic-test-data mistake and an off-by-one in an early draft of the
resourcing signature refactor).

## The country-scope cost model

This is the part that didn't exist in the spreadsheet version. Two knobs per additional
country, set on the Tool Rate Card:

- `countryDataOverheadHours` - a small flat addition (default 0.6-0.75h depending on
  tool) for a `DATA_ONLY` country: prepping test data and running one extra dry-run
  pass. No extra design work, because the UI/logic is identical.
- `countryLogicDeltaPct` - a fraction (default 35-40%) of the flow's own base design
  hours, charged again per `LOGIC_DIFFERENT` country, reflecting that a genuinely
  different approval chain or tax rule needs real design work even though it reuses
  the already-built page objects/components.

A flow with 5 `LOGIC_DIFFERENT` countries costs noticeably more than one with 5
`DATA_ONLY` countries - by design, and verified in `verify-engine.ts` Test 3.

**This is a judgment call per flow, not something to blindly trust from AI extraction.**
The AI defaults every multi-country flow's variant tag based on what the source
document says, but any flow with a `LOGIC_DIFFERENT` tag is automatically routed to the
Review Queue so a human confirms it's a real business-rule difference before it
inflates the estimate.

## The AI pipeline, and what it's actually trusted to decide

| Step | AI does this | A human/deterministic code does this |
|---|---|---|
| Extraction | Reads uploaded docs, proposes steps/personas/flags/countries per flow, with a confidence score | Zod validates the shape; anything below 70% confidence, or flagged AI-non-deterministic, or a logic-different country, lands in the Review Queue |
| Complexity scoring | — | Purely deterministic rubric (`rubric.ts`) - the AI never assigns a score directly, it only supplies the raw signals the rubric scores |
| Tool recommendation | Suggests a best-fit tool with rationale/caveats | The actual hour totals used for the estimate are always the deterministic engine's, never the AI's opinion |
| Rate-card calibration | — (deliberately no AI call here) | `calibration.ts` is plain statistics comparing logged actuals to original estimates; any proposal requires an admin's explicit approval before it changes future quotes |

The reasoning behind that last row: an LLM silently reshaping the numbers that drive
client-facing cost quotes is a bad idea even when it's *usually* right. Calibration
needs to be auditable and reproducible, so it isn't an AI call at all.

## What's verified vs. placeholder (be honest with yourself about this)

| Tool | Status |
|---|---|
| Selenium | Real benchmark, carried over from an actual delivery estimate (ATS ORC, extending an existing Selenium/Cucumber/TestNG framework on Oracle Fusion Redwood/JET) |
| UiPath | Setup hours (56) and the overall-effort comparison are real, from a Tool Comparison exercise against the same scope. Productivity rates are placeholders |
| Playwright | 100% illustrative. No source benchmark anywhere in this codebase |
| Tosca | 100% illustrative. No source benchmark anywhere in this codebase |

Every rate card shows its `confidence` and `sourceNote` in the UI (Settings → Rate
Cards) specifically so this is never hidden. **Do not quote a client off the Playwright
or Tosca numbers until you've calibrated them** - log a few real projects' actuals
against a saved estimate, then use "Propose Calibration from Actuals" on that tool's
card.

## Project structure

```
src/lib/estimation/     Pure logic - no DB, no Next.js, no AI. Explicit ".ts" import
                         extensions in this folder specifically so it runs directly
                         under `node --experimental-strip-types` with zero build step
                         (see scripts/verify-*.ts). rubric.ts, engine.ts, resourcing.ts
                         (also ships to the browser - it's what powers the instant
                         slider), calibration.ts.
src/lib/ai/              Anthropic API integration - extraction, tool recommendation,
                         retry-on-invalid-JSON wrapper.
src/lib/parsers/         docx/pdf/xlsx/csv → plain text, feeding the AI extraction step.
src/lib/validation/      Zod schemas - the boundary between "AI said this" and "this is
                         now in the database".
prisma/schema.prisma     Data model. Organization → Project → Flow/Upload/Estimate/
                         ActualLog; RateCard is org-scoped and versioned via
                         RateCardRevision for audit history.
src/app/api/             Route handlers. Every one calls requireSession() itself rather
                         than trusting middleware alone (see src/middleware.ts's comment
                         on why - Next.js has shipped middleware-bypass CVEs before).
src/app/, src/components/  UI. Server components fetch initial data; interactive bits
                         (the flows table, the resource planner, tool comparison) are
                         client components.
scripts/verify-*.ts      Zero-dependency correctness checks, see above.
```

## Roadmap / known limitations

Being direct about what this is *not*, so it doesn't surprise you later:

- **No SSO, no granular RBAC.** Auth is email/password via Auth.js credentials, with a
  single ADMIN/MEMBER distinction. Fine for an internal team tool; not fine as-is for
  a customer-facing product.
- **Global assumptions (hours/day, days/week, KT rates) are constants, not yet
  per-organization editable.** They're stable conventions, so this was a deliberate
  scope cut, not an oversight - see `src/lib/estimation/serverHelpers.ts`.
- **No column-mapped spreadsheet importer.** If your test-case register already has a
  fixed, trusted column layout, routing it through the AI extraction step is more
  expensive and less precise than a direct importer would be. That's a good next
  addition if your register format is stable.
- **No email/notifications, no password reset flow.** Use Prisma Studio
  (`npm run db:studio`) to fix a user's password by hand if needed.
- **Component-count input is still a manual judgment call** (Scope tab equivalent -
  "how many distinct UI components does this module touch"), same as it was in the
  spreadsheet version. Automating that would mean the AI reading actual UI screens or a
  codebase, not just requirement text - a bigger, different kind of extraction task.
- **Package versions**: researched as current at build time, but the JS ecosystem moves
  fast (Next.js and Prisma both shipped major versions recently). Run `npm install`,
  then `npm run typecheck`, and adjust anything npm/tsc flags before deploying for real.
