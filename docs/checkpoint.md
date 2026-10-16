# Prism Clock delivery checkpoint

Source candidate for reference R015. No APK release exists yet.

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
- `bash scripts/test-python-coverage.sh`: installer tests and 229 lines/80 branches all covered. Installer does not change Dream selection or timers; explicit ADB serial, download checksum and redirect validation, cancellation and removal consent remain enforced.
- Unsigned and uniquely signed APK builds pass.

First API34 Google TV native shader gate ran at actual 1920x1080. Captures and a ten-second native video in /tmp/prism-clock-first-native were inspected: full-frame continuously deformed metal, readable actual clock/date and slow clock travel. Settings screen was viewed, Back exited preview, app was stopped and all five secure Dream/timer keys were unchanged. This was candidate dc0b0d73, not the corrected final candidate. Exact log and capture provenance remain local.

The first capture revealed dotted seams and muted reflections. A red shared-edge regression proved one-ULP coordinate mismatches. A shared node grid now makes adjacent face edges bit-identical. A screen-space derivative seam path (with supported-extension detection and fallback) and stronger tone-mapped studio reflections are built in candidate 83e4432552d8183ea9e49588b7d8d3432ea746d7ff62a1ed9a617f7ac6fc881a. This corrected rendering passed a second API34 native compile/render check. /tmp/prism-clock-corrected-native/silver.png was viewed: shared seams are continuous and metal highlights stronger. Eleven schema/settings rows were read, a valid champagne update was persisted/read back, an unsupported value rejected, and all eleven settings restored exactly. Back and stop left the five secure keys unchanged. A bright facet reduced clock contrast, so the next candidate adds a soft moving contrast field and cached time/date formatting on a hardware-accelerated overlay. The contrast candidate passed native silver/champagne/iridescent rendering and actual nonpersistent bound Dream checks. Root viewed all three finishes and accepted the coherent, readable direction. Back was verified to clear the active Dream before force-stop. A later current preview-resource/focus build e7921f68 passed initially focused Preview, Select, Back, full D-pad settings scroll, Home and exact eleven-app/five-secure-value preservation. Current scene and settings captures are in art/screenshots with provenance in docs/verification.md. The embedded preview and launcher banner are inspected real native derivatives, not placeholders.

Source is public at https://github.com/jeremykenedy/prism-clock. Verified remote main is 3d764ace70c4c8e60b796d1bb3bb65e28f57262f; the descriptive feature branch adds the actual passing Build badge in draft PR2. Main verification and our PR checks passed. No release or tag exists. The owner's first star and All Activity watch were verified during repository setup.

All eight public banner variants were inspected in scoped Chrome media overrides at desktop1280/mobile320 widths, including separate stationary reduced-motion assets and animation samples across declared loop durations. All five actual native gallery images loaded and their desktop/mobile layouts were viewed. Browser media, viewport and owned windows/tabs were restored afterward. Details are in docs/verification.md.

Pending: full settings extremes and timed latest-build pacing/pause-resume, installer device check, final privacy artifact/runtime review, final source CI and quality gates, both host integrations, release and physical TV delivery. The authoring preview shape has been removed and replaced by an inspected real native JPEG. No physical/4K/performance support is claimed.

Shared emulator is exclusively assigned by root through /tmp/screensaver-emulator-5580-owner.json. Never use an old handoff message after compaction as authorization. Physical TV remains held until root coordinates current external activity.
