# Privacy and runtime boundary

The application source has no runtime network client, dependency, ads, analytics, tracking, telemetry, crash reporting, remote configuration or update checker. The built manifest requests no permissions. No data is uploaded or reported out.

Settings are local Android preferences. The exported settings provider exposes only the documented scene-setting schema and values so a local host can configure the app. It does not expose files, account information, device diagnostics or arbitrary commands. The local clock reads Android's configured time and locale without a content service.

The standalone Python installer is separate from the APK. When the user chooses the latest release, it requests GitHub's release metadata and downloads the APK and checksum, restricted to the configured repository and approved release-asset hosts. A local APK installation does not require those content downloads. The installer uses the selected ADB serial and does not change the TV's selected screensaver, system timers, permissions or security-update settings.

Static artifact checks have passed for the current local build. Runtime native networking observation and final release privacy review remain pending; see the delivery checkpoint.
