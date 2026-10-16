# Build and checks

Install a JDK compatible with Android build-tools, Python 3.10 or newer, Android SDK platform 36, build-tools 36.0.0 and ShellCheck. Set `ANDROID_HOME` if the SDK is not in `~/Library/Android/sdk`.

```sh
bash test.sh
bash scripts/test-coverage.sh
bash scripts/test-python-coverage.sh
bash scripts/check-style.sh
bash build.sh --unsigned
python3 scripts/check-artifact.py build/prism-clock-unsigned.apk
```

Coverage and style tools are developer-only downloads. JaCoCo and the Java formatter are pinned by SHA-256; Python development tools are version pinned. The app itself has no runtime dependencies or network permission.

For a local production signing build:

```sh
bash build.sh
```

The first signing build creates a unique RSA key outside the repository in `~/.android/prism-clock.jks` and a separate protected password file. Preserve both securely for future compatible upgrades. Signing configuration can use `PRISM_CLOCK_KEYSTORE` and `PRISM_CLOCK_KEYPASS`; these point to files and must not contain raw passwords. Restore an existing key rather than creating a replacement if the password file exists without its key. CI builds unsigned review artifacts and does not create or receive a production key.

The coverage gate includes only the Android-free mesh construction, settings resolution/validation and clock layout/time functions. It currently requires every line and branch in those three classes. The installer has a separate 100% line/branch gate. Native renderer, graphics shaders, Android provider, activity and Dream lifecycle are explicitly outside the host coverage boundary and require native tests and visual review. Host coverage does not establish rendering quality or TV behavior.
