# Ecosystem integrations

[Fire TV Toolkit](https://github.com/jeremykenedy/fire-tv-toolkit) and [Fire TV UI](https://github.com/jeremykenedy/fire-tv-ui) provide screensaver installation and selection workflows. Prism Clock publication and host integration are separate delivery steps. Do not assume a particular host version contains Prism until its catalog and installed artifact have been verified.

The package is `com.jeremykenedy.prismclock`, the Dream component is `com.jeremykenedy.prismclock/.PrismDreamService`, and the app-owned provider is documented in [configuration](configuration.md). A local installer does not change the selected Dream or timers. There is no runtime dependency on either host.

The unreleased 1.1.0 candidate adds `show_clock` to the same provider, bringing
its schema to twelve settings. It is a boolean, defaults to `true`, accepts only
`false` or `true`, and has `randomAllowed=false`. Hosts should preserve the eleven
existing values and render this row as a switch. A public APK pin must not change
until the native-verified release is published and its actual bytes are checked.
