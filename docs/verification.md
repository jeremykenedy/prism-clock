# Verification

## 1.1 optional clock

The final code 4 native candidate is 127,947 bytes, SHA-256
`6d1a3204ca648033c3c0c1d68adcceb29e90ed1ff61ec24b7f65e1326b7621e8`.
Its original production certificate remains
`193b1650f1e3924088d6084932a4ec5fffe36ae95f6a8cb943bfa10c4bde4788`.
The twelve-row settings interface adds Show clock, default true, with
randomAllowed false. Off removes time, date, period marker and backing while
leaving the surface moving. The renderer, mesh and both shaders are byte-identical
in source to the published 1.0 design. This update adds visibility control and
makes no performance-improvement claim.

### Physical Fire TV API 28

The exact code 4 signed APK replaced the preceding same-signer build in place.
All twelve existing choices remained identical. Actual D-pad operation toggled
Show clock OFF and ON, opened Preview through the native button and returned
with Back. Current unretouched 1920x1080 images show the complete overlay absent,
then local time/date visible, and the unchecked native switch. A separate 10-second
OFF recording shows continuous deformation.

At the default silver/gentle/medium/sculpted/balanced choices, three clean rolling
content-SurfaceView windows measured 18.204 presents/s with the clock OFF.
Clock ON measured 17.123 to 17.162 presents/s. Largest within-buffer gaps were
about 66.70ms. Recording did not overlap these windows. This is a measured
hardware limitation, not universal24/30FPS support or a thermal test. Version1.0
was not independently timed on this same device.

The earlier 1.1/code 2 runtime also passed actual natural idle activation after
302.147 seconds, followed by 301.847 seconds bound as a normal, non-test
PrismDreamService with the overlay hidden. Back exited. Six late rolling
SurfaceView buffers measured 17.647 to 17.714 presents/s. The final code 4 APK has
all code 2 ZIP payloads byte-identical except AndroidManifest.xml's version;
its exact Preview/controls were checked, but natural idle was not repeated on4.
These are separate observations and do not establish a continuous timing trace.

Final code 4 cleanup compared 49 launcher preferences, 19 system values, surrounding
APK hashes, readable providers, external backup/import bytes and existence,
package and UID app-op modes, actual API 28 accessibility service listing and guard
state. All matched. No timer, audio, account, network or permission changes were
made. The original selected Black Dream was preserved. Prism was explicitly
stopped after the final provider queries, and the TV returned awake on Main.

### Google TV API 34 emulator

The code 2 candidate verified the eleven-value upgrade, default-true new switch,
actual D-pad ON/OFF, randomization independence, process-death persistence and
same-ActivityRecord Preview Home/resume/Back. Both stored clock values survived
randomize_all. The switch rejects random without mutation. The eight-second OFF
native movie was independently played at normal speed with changing facets and
no time, date, period marker or backing. These runtime payloads match code 4.

The native gate restored launcher/system settings, surrounding query-readable
providers and APK hashes, external bytes/existence, app-op modes, accessibility,
network and stopped guard. The same AVD returned alive on Google Home. No
physical-device, restart or account claims follow from emulator results.

The first harness case-folded a button label incorrectly; it stopped and restored
state before toggling. The first physical sampler looked for the package in a
generic Dream layer name and produced no usable windows. Corrected observations
above use actual runtime state and the observed layer, not zero-FPS guesses.

### Scope

The preserved original surface was visually accepted by the owner. This release
fulfills the separate clock-visibility request. Faster rendering on the measured
API 28 hardware remains open. Reboot persistence, sustained thermal/power behavior,
native 4K and other physical Android TV/Google TV models remain unverified.
Current screenshot provenance is linked in the [gallery](screenshots.md).
Primary-branch CI, signed publication and both host integrations are separate
release gates recorded in the [checkpoint](checkpoint.md).

## Historical final 1.0.0 verification

## October 22 physical Fire TV

The frozen original-key signed APK `edbf475c4a2b253ccc069adaee8cac2cbba4d16b5579d5299e2e2d1bdaa21fd5` ran on a Fire TV AFTDEC012E/API 30. The silver/flowing/small-facets/dramatic/bright/large-clock/wide-travel/24-hour/seconds/date preset warmed for 40 seconds. Two production SurfaceView samples measured 24.5899 and 24.6709 actual presents per second across 126 presents each; the second median was 33.395 ms and maximum 50.195 ms. This clears 24 FPS with limited headroom. It is not encoded movie FPS, a thermal endurance test or native 4K proof.

The normal-speed eight-second physical recording showed continuous facet deformation and legible local time/date. Remote Settings reached Preview; Menu left Preview active, Back returned Settings and Home exited. A graphite/slow/large-facets/subtle/dim/small-clock/gentle-travel/12-hour/no-seconds/no-date preset then ran as the actual selected non-test Dream and exited with Back.

Before the first physical update, the installed settings provider exposed no values, so pre-update preference retention is not established there. All 11 post-update values were captured and restored. A bounded per-key write/readback retry resolved an initial graphite write mismatch before the minimum preset was accepted. The API 34 in-place upgrade separately retained 11 pre-existing provider values. All eight protected system settings, other screensaver preferences, UI backup and foreground protection were preserved; the original actual selected Aquarium Dream was restored. No timer changes, data clear or uninstall occurred.

The full bundled NOTICE/license scrolling and positive Close action passed on the exact APK on API 34, with Preview/Back and bound Dream/Back. Physical screenshots and exact hashes appear in the [current gallery](screenshots.md).

Automatic idle activation, reboot, long thermal/power runs, native 4K and other physical Android TV/Google TV models remain untested. The earlier development history below records separate artifacts and does not replace this final proof.

## Host checks

The explicit Android-free boundary covers mesh generation, options validation/resolution and clock/time layout: 66 lines and 64 branches, all covered. Installer coverage is 229 statements and 80 branches, all covered. Actual source formatting, Python lint/format, shell checks, artifact/privacy inspection and signed build pass. These counts exclude Android framework lifecycle, provider persistence and graphics quality. Required CI is checked on the exact remote primary before publication.

## Public documentation browser checks

The public GitHub README was inspected in Chrome at 1280px and 320px viewport widths. Scoped DevTools media overrides selected all eight desktop/mobile, light/dark, animated/still banner variants. Each expected content-hashed `currentSrc` loaded completely. The 320px mobile banner rendered within a 254px content box without clipped titles or artwork. Animated views were sampled across their declared loop durations; facets changed and moved within their illustration bounds while text remained fixed. Reduced-motion views selected the separate stationary artwork. These checks concern documentation animation, not native scene pacing.

All five real native gallery images loaded at their original 1920px source width in both viewport layouts. Desktop and mobile gallery layouts were visually inspected. Both scoped media overrides were restored to No emulation, device toolbar remained off, the viewport override was reset, and only the owned review tab and DevTools window were closed.

## October 22 exact credits candidate

Android API 34 emulator checks used the frozen APK hash above with actual 1920x1080 captures. D-pad navigation reached Credits and licenses, scrolled through the full NOTICE and license to its end, and selected the positive Close button. Preview returned to Settings with Back; the actual DreamService bound and exited with Back. All eight saved system keys and the original screensaver selection were unchanged, and the emulator returned Home. No app data was cleared or uninstalled.

Prism was already installed; its 11 exposed preferences were unchanged by the signed in-place update and restored after testing. These are native credits/lifecycle checks, not physical smoothness, automatic idle, 4K or thermal verification.

[Sanitized capture provenance](../art/screenshots/credits-api34.json) records the artifact and screenshot hashes.

<table>
  <tr>
    <td><a href="../art/screenshots/credits-api34-credits.png"><img src="../art/screenshots/credits-api34-credits.png" alt="Full offline credits and notices" width="320"></a></td>
    <td><a href="../art/screenshots/credits-api34-license-end.png"><img src="../art/screenshots/credits-api34-license-end.png" alt="End of the bundled Apache license" width="320"></a></td>
    <td><a href="../art/screenshots/credits-api34-settings.png"><img src="../art/screenshots/credits-api34-settings.png" alt="Current native settings" width="320"></a></td>
  </tr>
</table>
