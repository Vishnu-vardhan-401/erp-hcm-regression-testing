/** @type {import('next').NextConfig} */
const nextConfig = {
  eslint: {
    // Linting is still available via `npm run lint`; not blocking production builds
    // keeps the container image build from failing on style-only issues.
    ignoreDuringBuilds: true,
  },
  serverExternalPackages: ['pdf-parse', 'mammoth'],
};

export default nextConfig;
