# Ecosystem integrations

[Fire TV Toolkit](https://github.com/jeremykenedy/fire-tv-toolkit) and [Fire TV UI](https://github.com/jeremykenedy/fire-tv-ui) provide screensaver installation and selection workflows. Prism Clock publication and host integration are separate delivery steps. Do not assume a particular host version contains Prism until its catalog and installed artifact have been verified.

The package is `com.jeremykenedy.prismclock`, the Dream component is `com.jeremykenedy.prismclock/.PrismDreamService`, and the app-owned provider is documented in [configuration](configuration.md). A local installer does not change the selected Dream or timers. There is no runtime dependency on either host.
