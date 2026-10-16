package com.jeremykenedy.prismclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ClockLayout {
  private ClockLayout() {}

  public static String time(long epoch, boolean use24Hour, boolean seconds, Locale locale) {
    return new SimpleDateFormat((use24Hour ? "HH:mm" : "h:mm") + (seconds ? ":ss" : ""), locale)
        .format(new Date(epoch));
  }

  public static String date(long epoch, Locale locale) {
    return new SimpleDateFormat("EEEE, MMMM d", locale).format(new Date(epoch));
  }

  public static float driftX(float seconds, float travel) {
    return travel * (float) (.73 * Math.sin(seconds * .019) + .27 * Math.sin(seconds * .031 + 1.2));
  }

  public static float driftY(float seconds, float travel) {
    return travel * (float) (.7 * Math.sin(seconds * .023 + .8) + .3 * Math.cos(seconds * .013));
  }

  public static float step(long elapsedMillis) {
    return Math.max(0, Math.min(elapsedMillis, 100)) * .001f;
  }
}
