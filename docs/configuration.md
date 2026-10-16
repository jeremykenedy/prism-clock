# Configuration

Open Prism Clock from the TV launcher, use the remote to select a control, and choose a value. Changes apply on the next preview or Dream start. Each choice also supports Random, resolved once when the scene starts. Randomize all resolves every setting once per start; it does not flicker between configurations during playback.

| Key | Choices | Default | Effect |
| --- | --- | --- | --- |
| `palette` | silver, graphite, champagne, iridescent | silver | Metal reflectance; iridescent adds view-angle-dependent coating color. |
| `speed` | slow, gentle, flowing | gentle | Continuous surface deformation speed. |
| `facets` | large, medium, small | medium | Triangular mesh scale, using 24, 36 or 52 columns. |
| `depth` | subtle, sculpted, dramatic | sculpted | Height and resulting normal variation across the metal surface. |
| `brightness` | dim, balanced, bright | balanced | Scene lighting strength. |
| `clock_size` | subtle, standard, large | standard | Time and date typography scale. |
| `clock_drift` | gentle, wide | gentle | Continuous clock travel. All choices move. |
| `clock_format` | 12h, 24h | 24h | Actual local time format; 12-hour mode includes the locale's period marker. |
| `show_seconds` | false, true | false | Seconds in the clock. |
| `show_date` | false, true | true | Actual local weekday and date. |
| `randomize_all` | false, true | false | Choose each setting randomly on scene start. |

The local time zone and locale come from Android. Prism Clock does not fetch time, location or weather.

The host integration interface is `content://com.jeremykenedy.prismclock.settings/schema` for the version-one schema and `/settings` for current values. The schema returns `key`, `title`, `type`, `default`, `choices`, and `randomAllowed`. Updates use the `/settings` URI with string `key` and `value` fields. Unknown keys, unsupported values and updates to the schema are rejected. No host receives arbitrary file or command access through this provider.

The Settings activity is `com.jeremykenedy.prismclock/.SettingsActivity`; the Dream component is `com.jeremykenedy.prismclock/.PrismDreamService`. The provider and native remote behavior still require the pending device verification recorded in the checkpoint.
