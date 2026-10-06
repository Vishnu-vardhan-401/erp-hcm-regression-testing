import { PrismaClient } from '@prisma/client';
import bcrypt from 'bcryptjs';
import { DEFAULT_RATE_CARDS } from '../src/lib/estimation/rateCardDefaults';

const db = new PrismaClient();

async function main() {
  const orgName = process.env.SEED_ORG_NAME || 'My Organization';
  const adminEmail = process.env.SEED_ADMIN_EMAIL || 'admin@example.com';
  const adminPassword = process.env.SEED_ADMIN_PASSWORD || 'change-me-before-first-login';

  let org = await db.organization.findFirst({ where: { name: orgName } });
  if (!org) {
    org = await db.organization.create({ data: { name: orgName } });
    console.log(`Created organization "${orgName}" (${org.id})`);
  } else {
    console.log(`Organization "${orgName}" already exists (${org.id})`);
  }

  const existingAdmin = await db.user.findUnique({ where: { email: adminEmail } });
  if (!existingAdmin) {
    const passwordHash = await bcrypt.hash(adminPassword, 10);
    const admin = await db.user.create({
      data: {
        email: adminEmail,
        name: 'Admin',
        passwordHash,
        role: 'ADMIN',
        organizationId: org.id,
      },
    });
    console.log(`Created admin user ${admin.email} - CHANGE THIS PASSWORD after first login.`);
  } else {
    console.log(`Admin user ${adminEmail} already exists - skipping.`);
  }

  for (const rc of Object.values(DEFAULT_RATE_CARDS)) {
    const existing = await db.rateCard.findFirst({ where: { organizationId: org.id, tool: rc.tool, isActive: true } });
    if (existing) {
      console.log(`Rate card for ${rc.tool} already exists - skipping (edit it from Settings instead of re-seeding).`);
      continue;
    }
    await db.rateCard.create({
      data: {
        organizationId: org.id,
        tool: rc.tool,
        confidence: rc.confidence,
        sourceNote: rc.sourceNote,
        setupHours: rc.setupHours,
        hoursPerComponent: rc.hoursPerComponent,
        reusePct: rc.reusePct,
        designScriptsPerDay: rc.designScriptsPerDay,
        execScriptsPerDay: rc.execScriptsPerDay,
        pmPct: rc.pmPct,
        contingencyPct: rc.contingencyPct,
        countryDataOverheadHours: rc.countryDataOverheadHours,
        countryLogicDeltaPct: rc.countryLogicDeltaPct,
        serialFloorWeeks: rc.serialFloorWeeks,
        team: rc.team,
        licenseModel: rc.licenseModel,
      },
    });
    console.log(`Seeded rate card: ${rc.tool} (${rc.confidence})`);
  }

  console.log('\nSeed complete. Log in with:');
  console.log(`  email:    ${adminEmail}`);
  console.log(`  password: ${adminPassword}`);
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await db.$disconnect();
  });
