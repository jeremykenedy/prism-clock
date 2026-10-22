# Verification

## Host logic and artifact checks

Mesh construction, options and clock logic currently cover 66 lines and 64 branches at 100% using the explicit JaCoCo host boundary. Geometry tests exposed and prevented alternating stretched triangle rows and non-identical shared edges. Installer coverage is gated separately and includes interrupted CLI cancellation. Android graphics, settings/provider and lifecycle are native checks rather than host-coverage claims.

The unsigned artifact inspection checks package/version, required DreamService, settings-provider authority and absence of requested permissions. The production key is unique to Prism Clock and remains outside source control.

## Current native evidence

Google TV API34 emulator `emulator-5580`, host GPU, actual 1920x1080 captures, 2026-10-16. The scene capture APK SHA-256 is `88004170f5969ac69baa8b35454cd4495b7ba1901cc5efaa532592ebed9320f9`. Later initial-focus and preview-resource changes do not change its scene geometry, shaders or typography.

| Capture | Provenance and settings |
| --- | --- |
| `art/screenshots/prism-silver-dream-api34.png` | Actual bound PrismDreamService; Silver, Gentle, Medium facets, Sculpted, Balanced light, Standard clock, Gentle travel, 24-hour, seconds off, date on, randomize-all off. |
| `art/screenshots/prism-champagne-api34.png` | Native PreviewActivity, identical settings except Champagne finish. |
| `art/screenshots/prism-iridescent-api34.png` | Native PreviewActivity, identical settings except Iridescent finish. |

The first preview settled for at least forty seconds. Subsequent finish restarts and the actual Dream capture were inspected after their new surfaces rendered. Every stored screenshot was opened and viewed. The embedded 640x360 JPEG and 320x180 launcher banner are resized derivatives of the actual silver Dream capture; no scene content was retouched.

The Android14 AOSP `IDreamManager.aidl` source was inspected to confirm the exact-image hidden Binder `testDream` mapping, matching a previously verified gate on this same API34 emulator. A nonpersistent test Dream was started, the current Dream component verified in `dumpsys dreams`, and its actual pixels captured. Back was separately checked before force-stop and returned `mCurrentDream=null`. This mapping is not a public installer contract and is not assumed valid on other Android or Fire OS versions.

Native settings schema and value queries returned eleven rows. A valid finish change persisted and was read back; an invalid value was rejected. Exact defaults were restored. Remote Down/Select opened Preview from Settings, Back returned Settings, and Home left. The initial Preview-focus correction passed on the next native build, SHA-256 `e7921f68edbde6b151220051fff00e8ac76815fed3065094483508fc94d920a9`: accessibility state confirms the Preview button is initially focused, Select opens Preview directly, Back returns Settings and D-pad reaches the bottom Randomize control. Current top/bottom settings captures were opened and viewed and are stored in `art/screenshots/prism-settings-top-api34.png` and `prism-settings-bottom-api34.png`. All eleven app values remained identical to the baseline.

All five secure keys remained unchanged: selected Dream components, enabled state, activate-on-sleep, activate-on-dock, and sleep timeout. The selected Reel Atrium component and enabled1 baseline were preserved. No settings or permission grants were added.

The first ten-second native development movie showed continuous surface deformation and clock travel. Current finish captures establish native rendering and readability, but a final current-build timed motion/pacing check remains pending. No displayed FPS is inferred from captured movie frames or display refresh rate.

## Public documentation browser checks

The public GitHub README was inspected in Chrome at 1280px and 320px viewport widths. Scoped DevTools media overrides selected all eight desktop/mobile, light/dark, animated/still banner variants. Each expected content-hashed `currentSrc` loaded completely. The 320px mobile banner rendered within a 254px content box without clipped titles or artwork. Animated views were sampled across their declared loop durations; facets changed and moved within their illustration bounds while text remained fixed. Reduced-motion views selected the separate stationary artwork. These checks concern documentation animation, not native scene pacing.

All five real native gallery images loaded at their original 1920px source width in both viewport layouts. Desktop and mobile gallery layouts were visually inspected. Both scoped media overrides were restored to No emulation, device toolbar remained off, the viewport override was reset, and only the owned review tab and DevTools window were closed.

Source is public on `main` and the descriptive feature branch. The initial main verification workflow and current source PR checks passed. Source publication is not an APK release or a completed device gate.

## Remaining release gates

- Complete settings extremes and persistence, current timed playback and pause/resume.
- Final APK privacy/runtime, signer and package/version inspection after preview resources settle.
- Final required CI after the final production sweep, followed by the root quality gate.
- Physical Fire TV automatic idle activation, vendor Back/Menu/Home behavior, sustained performance, power/thermal and actual buffer resolution.
- Other physical Android TV / Google TV combinations. Volunteers with untested devices are needed to report model, OS/API, resolution, behavior and results through the repository's issue tracker.
- Release and both host integrations, with final checksum verification and physical replacement delivery.

No release, automatic-idle, physical TV, native 4K or sustained performance support is claimed yet.

## Offline credits follow-up

The current credits-only artifact is `edbf475c4a2b253ccc069adaee8cac2cbba4d16b5579d5299e2e2d1bdaa21fd5`. It adds a final remote-accessible Settings button and bundles the original NOTICE and complete Apache license, preserving the Cloud Drift Clock source notice. All original non-DEX entries and compiled classes outside SettingsActivity match the prior e792 renderer baseline. The whole APK and DEX differ. Source formatting, host line/branch coverage, installer coverage, signed build and permission/manifest inspection pass. The exact final credits/scroll/Preview/Dream/Back routes, updated Settings capture and physical moving/pacing acceptance remain pending. No release is approved by these host checks.
