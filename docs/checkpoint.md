# Prism Clock delivery checkpoint

Unpublished work in progress for reference R015. No GitHub repository or release exists yet.

- Workspace: /Users/jeremykenedy/sites/prism-clock
- Branch: create-metallic-prism-clock
- Package: com.jeremykenedy.prismclock
- Dream: com.jeremykenedy.prismclock/.PrismDreamService
- Settings provider: content://com.jeremykenedy.prismclock.settings/schema and /settings
- Version: 1.0.0 / 1
- Unique production signer is outside the repository in ~/.android/prism-clock.jks with separate protected password file.

The scene is an original triangulated metal surface. Three displaced corners define each face normal in the GPU shader. View-dependent studio reflections, thin seams and metallic finishes move with the deforming surface. A native clock overlay uses the actual local time and date with continuous slow travel. Geometry is uploaded once to a VBO; frame scheduling is capped near 30 updates per second and stops on pause. This scheduling target is not measured performance.

Settings: metal finish, surface motion, facet size, depth, lighting, clock size/travel/time format, seconds, date, per-setting random and randomize-all. All choices have actual renderer or clock behavior. Runtime has no network permission or dependencies.

Verified host checks:

- `bash test.sh`: geometry, consistent triangular proportions, settings validation/random resolution, time/date formatting, bounded resume timing and clock safe travel.
- A red regression exposed alternating stretched rows, then passed after the diagonal topology correction.
- `bash scripts/test-coverage.sh`: 66 owned Android-free lines and 64 branches, all covered. Android graphics/provider/lifecycle behavior is outside this host boundary and requires native checks.
- `bash scripts/test-python-coverage.sh`: installer tests and 226 lines/80 branches all covered. Installer does not change Dream selection or timers; explicit ADB serial, download checksum and redirect validation, cancellation and removal consent remain enforced.
- Unsigned and uniquely signed APK builds pass.

First API34 Google TV native shader gate ran at actual 1920x1080. Captures and a ten-second native video in /tmp/prism-clock-first-native were inspected: full-frame continuously deformed metal, readable actual clock/date and slow clock travel. Settings screen was viewed, Back exited preview, app was stopped and all five secure Dream/timer keys were unchanged. This was candidate dc0b0d73, not the corrected final candidate. Exact log and capture provenance remain local.

The first capture revealed dotted seams and muted reflections. A red shared-edge regression proved one-ULP coordinate mismatches. A shared node grid now makes adjacent face edges bit-identical. A screen-space derivative seam path (with supported-extension detection and fallback) and stronger tone-mapped studio reflections are built in candidate 83e4432552d8183ea9e49588b7d8d3432ea746d7ff62a1ed9a617f7ac6fc881a. This corrected rendering has NOT yet had native review.

Pending: corrected actual native shader/visual review, timed motion and readability, settings/provider persistence, actual Dream lifecycle, remote navigation, screenshots and actual embedded preview, installer device test, privacy artifact/runtime review, README/banner/badges and full source CI, public source/release metadata, final quality gates, both host integrations and physical TV delivery. The local preview shape is an authoring placeholder and must be replaced by an inspected real native capture before publication. No physical/4K/performance support is claimed.

Shared emulator is exclusively assigned by root through /tmp/screensaver-emulator-5580-owner.json. Never use an old handoff message after compaction as authorization. Physical TV remains held until root coordinates current external activity.
