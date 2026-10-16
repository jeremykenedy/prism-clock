# Project standards

- Render a continuously moving original metallic triangular surface and actual local clock.
- Keep runtime offline: no INTERNET permission, analytics, telemetry, ads or reporting.
- Preserve com.jeremykenedy.prismclock and its unique production signer for upgrades.
- Stop graphics and clock callbacks when the activity or dream pauses or detaches.
- Render to the Android surface size. Do not force 4K or infer performance from refresh rate.
- Keep meaningful settings remote accessible and expose the app-owned schema provider.
- Keep keys and build outputs outside Git. Use Apache-2.0 and preserve notices.
- Require native visual and lifecycle review before release. A build is not visual acceptance.
