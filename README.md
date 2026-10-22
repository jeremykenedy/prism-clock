<p align="center">
  <a href="https://github.com/jeremykenedy/prism-clock#gh-light-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-light.f3c2c92812dc.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-light.8d7269506a81.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-light.46f30ec35a1d.svg"><img src="art/banner-light.a76a63a602a7.svg#gh-light-mode-only" alt="Prism Clock" width="800"></picture></a>
  <a href="https://github.com/jeremykenedy/prism-clock#gh-dark-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-dark.f707cdce7e17.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-dark.d2d168ce9fc5.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-dark.be2bc8b36a84.svg"><img src="art/banner-dark.b1b341dfadb6.svg#gh-dark-mode-only" alt="Prism Clock" width="800"></picture></a>
</p>

<p align="center">A native clock over a continuously deforming metallic triangular surface.</p>

<p align="center"><a href="https://github.com/jeremykenedy/prism-clock/releases"><img src="https://img.shields.io/github/downloads/jeremykenedy/prism-clock/total" alt="Release downloads"></a> <a href="https://github.com/jeremykenedy/prism-clock/releases/latest"><img src="https://img.shields.io/github/v/release/jeremykenedy/prism-clock" alt="Latest stable release"></a> <a href="https://github.com/jeremykenedy/prism-clock/actions/workflows/ci.yml"><img src="https://github.com/jeremykenedy/prism-clock/actions/workflows/ci.yml/badge.svg?branch=main" alt="Build and coverage tests"></a> <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg" alt="Apache License 2.0"></a></p>

<p align="center"><a href="https://github.com/jeremykenedy"><img src="https://img.shields.io/github/followers/jeremykenedy?label=Follow" alt="Follow Jeremy Kenedy"></a> <a href="https://github.com/jeremykenedy/prism-clock"><img src="https://img.shields.io/badge/GitHub-Star-blue" alt="Star Prism Clock"></a> <a href="https://github.com/sponsors/jeremykenedy"><img src="https://img.shields.io/badge/GitHub-Sponsor-pink" alt="Sponsor on GitHub"></a></p>

Show some love by [starring this repository on GitHub](https://github.com/jeremykenedy/prism-clock).

Prism Clock shows actual local time over a continuously moving metallic surface. The final 1.0.0 build passed native emulator and physical Fire TV preview, settings and selected-Dream checks. See [verification](docs/verification.md) for measured performance and limits.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Features](#features)
- [Configuration](#configuration)
- [Device Verification](#device-verification)
- [Testing](#testing)
- [Documentation](#documentation)
- [Screenshots](#screenshots)
- [Privacy](#privacy)
- [License](#license)

## Requirements

The APK targets Android API 36 with a minimum API of 23 and requires OpenGL ES 2.0. These declared requirements do not establish compatibility with every device. See the verification matrix below.

Source builds need Java, Python 3.10 or newer, Android SDK platform 36 and build-tools 36.0.0. Style checks also need ShellCheck. The app has no runtime dependencies.

## Installation

Download the signed APK from [the latest release](https://github.com/jeremykenedy/prism-clock/releases/latest), or use the guided installer:

```sh
python3 install.py --serial YOUR_ADB_SERIAL
```

It downloads the latest stable APK and verifies its checksum. For a local build, add `--apk build/prism-clock.apk`.

The installer asks before changing the selected device. Updates use Android's same-package, same-signer replacement and preserve app settings. It does not change the TV's selected screensaver or system timers. Choose Prism Clock through the TV's supported screensaver picker after installation. Availability of that picker depends on the TV platform.

Open Prism Clock from the launcher to configure the scene or select Preview animation. The preview is separate from automatic Dream activation.

To remove the app and its local preferences:

```sh
python3 install.py --serial YOUR_ADB_SERIAL --uninstall
```

If Prism Clock is the selected screensaver, select another screensaver through the supported TV picker before removal. The installer has no prior Dream state to restore because it does not change selection or timers.

## Features

| Feature | What it does | Guide |
| --- | --- | --- |
| Moving metal | Deforming triangular surface with four studio-lit finishes | [Scene options](docs/configuration.md) |
| Local clock | Actual 12/24-hour time, optional seconds/date and continuous travel | [Clock controls](docs/configuration.md) |
| Remote controls | Native settings, preview and offline credits | [Device verification](docs/verification.md) |
| Host integration | App-owned settings schema with validated values | [Provider contract](docs/configuration.md) |

## Configuration

Scene changes apply on the next preview or Dream start. Useful controls are described in [Configuration](docs/configuration.md), including their keys, choices, defaults and actual rendering effects.

The app-owned host interface is `content://com.jeremykenedy.prismclock.settings/schema` and `/settings`. Unsupported keys and values are rejected. This is a local scene-settings interface, not an arbitrary command or file-access bridge.

## Device Verification

| Environment | Resolution | Verified behavior | Status |
| --- | --- | --- | --- |
| Google TV API 34 emulator | 1920x1080 | Scene finishes, settings validation/persistence, full offline license/Close, Preview/Back/Home and bound Dream | Native routes passed |
| Fire TV AFTDEC012E/API 30 | 1920x1080 | Maximum silver preset, minimum graphite selected non-test Dream and remote exits | 24.67 actual presents/s after warmup; limited headroom |
| Other physical Android TV / Google TV | Unverified | None claimed | Device testing needed |

If you have an untested device, please report the model, OS/API, resolution, tested behavior and results through the repository's issue tracker. Emulator preview behavior does not prove automatic idle activation, vendor navigation, native 4K composition or sustained hardware performance.

## Testing

```sh
bash test.sh
bash scripts/test-coverage.sh
bash scripts/test-python-coverage.sh
bash scripts/check-style.sh
bash build.sh --unsigned
python3 scripts/check-artifact.py build/prism-clock-unsigned.apk
```

The explicit Android-free host coverage boundary currently passes every line and branch in mesh generation, options resolution/validation and clock/time layout. The installer has a separate 100% line/branch gate. Native graphics, provider persistence, lifecycle, remote navigation and displayed quality require separate platform tests. See [Building](docs/building.md) and the [Delivery checkpoint](docs/checkpoint.md).

CI builds unsigned review APKs and enforces the explicit host coverage, installer coverage, style, artifact and documentation checks. Production signing keys stay outside CI.

## Documentation

- [Architecture](docs/architecture.md)
- [Building and coverage boundaries](docs/building.md)
- [Configuration and host interface](docs/configuration.md)
- [Privacy and installer network boundary](docs/privacy.md)
- [Native capture provenance and remaining gates](docs/verification.md)
- [Delivery checkpoint and pending gates](docs/checkpoint.md)
- [Native screenshot gallery](docs/screenshots.md)
- [Ecosystem integrations](docs/integrations.md)
- [Version 1.0.0 release guide](docs/releases/v1.0.0.md)

## Screenshots

<table>
<tr><td><img src="art/screenshots/physical-silver-preview.png" alt="Physical Fire TV silver surface with a large drifting clock" width="320"></td><td><img src="art/screenshots/physical-graphite-dream.png" alt="Physical selected graphite Dream with a smaller 12-hour clock" width="320"></td><td><img src="art/screenshots/credits-api34-credits.png" alt="Current offline credits on the API 34 emulator" width="320"></td></tr>
</table>

The first two 1920x1080 captures show the exact 1.0.0 physical build; credits use the same APK on an API 34 emulator. See the [full current gallery](docs/screenshots.md) for settings, the complete license and artifact provenance.

## Privacy

The APK has no network permission, runtime dependencies, ads, analytics, tracking, telemetry or reporting. Scene preferences remain local. The standalone installer separately downloads explicitly requested GitHub releases and verifies checksums; it is not part of the app runtime. The final signed artifact requests no network permission. See [Privacy](docs/privacy.md).

## License

This project is open-sourced software licensed under the [Apache License 2.0](LICENSE). See [NOTICE](NOTICE) for the adapted native scaffolding notice. Surface geometry, shaders and banner art are original project assets.
