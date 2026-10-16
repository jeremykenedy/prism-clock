package com.jeremykenedy.prismclock;

import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public final class PrismTest {
  private static int checks;

  private static void check(boolean condition, String message) {
    checks++;
    if (!condition) throw new AssertionError(message);
  }

  private static String[] defaults() {
    String[] values = new String[PrismOptions.SCHEMA.length];
    for (int i = 0; i < values.length; i++) values[i] = PrismOptions.SCHEMA[i][2];
    return values;
  }

  public static void main(String[] args) {
    for (int columns : new int[] {4, 24, 36, 52, 80}) {
      float[] mesh = PrismMesh.create(columns);
      check(mesh.length % 33 == 0, "complete triangles");
      java.util.Map<String, Integer> edges = new java.util.HashMap<>();
      for (int i = 0; i < mesh.length; i += 33) {
        for (int edge = 0; edge < 3; edge++) {
          int a = i + edge * 11, b = i + ((edge + 1) % 3) * 11;
          String from = Float.floatToIntBits(mesh[a]) + ":" + Float.floatToIntBits(mesh[a + 1]);
          String to = Float.floatToIntBits(mesh[b]) + ":" + Float.floatToIntBits(mesh[b + 1]);
          String key = from.compareTo(to) < 0 ? from + "/" + to : to + "/" + from;
          edges.put(key, edges.containsKey(key) ? edges.get(key) + 1 : 1);
        }
        float x0 = mesh[i],
            y0 = mesh[i + 1],
            x1 = mesh[i + 11],
            y1 = mesh[i + 12],
            x2 = mesh[i + 22],
            y2 = mesh[i + 23];
        check(
            (x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0) > 0, "nondegenerate front-facing facet");
        double side0 = Math.hypot(x1 - x0, y1 - y0),
            side1 = Math.hypot(x2 - x1, y2 - y1),
            side2 = Math.hypot(x0 - x2, y0 - y2);
        check(
            Math.abs(side0 - side1) < .00001 && Math.abs(side0 - side2) < .00001,
            "consistent equilateral base facets, no alternating stretched rows");
        for (int corner = 0; corner < 3; corner++) {
          int start = i + corner * 11;
          check(
              mesh[start + 8] + mesh[start + 9] + mesh[start + 10] == 1,
              "barycentric seam weights");
          check(
              mesh[start + 2] == x0
                  && mesh[start + 3] == y0
                  && mesh[start + 4] == x1
                  && mesh[start + 5] == y1
                  && mesh[start + 6] == x2
                  && mesh[start + 7] == y2,
              "all corners share exactly one flat normal basis");
        }
      }
      int boundary = 0;
      for (int users : edges.values()) {
        check(users <= 2, "no overlapping or duplicate facets");
        if (users == 1) boundary++;
      }
      check(
          boundary == 2 * (columns + (int) Math.ceil(columns * .7)),
          "all internal edges bit-identical and watertight");
    }
    for (int columns : new int[] {3, 81}) {
      try {
        PrismMesh.create(columns);
        throw new AssertionError("invalid density accepted");
      } catch (IllegalArgumentException expected) {
        checks++;
      }
    }
    PrismOptions base = new PrismOptions(defaults(), new Random(1), false);
    check(
        base.palette == 0 && base.columns == 36 && base.depth == .36f && base.speed == .34f,
        "scene defaults");
    check(
        base.use24Hour && !base.seconds && base.date && base.travel > 0,
        "clock always drifts by default");
    for (int row = 0; row < PrismOptions.SCHEMA.length; row++) {
      String[] spec = PrismOptions.SCHEMA[row];
      check(PrismOptions.row(spec[0]) == row, "schema discoverable");
      for (int i = 3; i < spec.length; i++) {
        String[] values = defaults();
        values[row] = spec[i];
        check(PrismOptions.supported(spec[0], spec[i]), "schema value accepted");
        PrismOptions option = new PrismOptions(values, new Random(2), false);
        check(
            option.speed > 0 && option.columns >= 24 && option.travel > 0,
            "all supported scenes remain moving");
      }
      String[] invalid = defaults();
      invalid[row] = "unknown";
      new PrismOptions(invalid, new Random(3), false);
      invalid[row] = null;
      new PrismOptions(invalid, new Random(3), false);
      check(
          !PrismOptions.supported(spec[0], "unknown") && !PrismOptions.supported(spec[0], null),
          "bad values rejected");
      invalid[row] = "random";
      new PrismOptions(invalid, new Random(4), false);
      check(PrismOptions.supported(spec[0], "random"), "per-setting random supported");
    }
    check(
        !PrismOptions.supported(null, "true") && !PrismOptions.supported("unknown", "true"),
        "unknown keys rejected");
    check(
        PrismOptions.supported("randomize_all", "true")
            && PrismOptions.supported("randomize_all", "false")
            && !PrismOptions.supported("randomize_all", "random"),
        "random switch validation");
    for (int seed = 0; seed < 100; seed++) new PrismOptions(defaults(), new Random(seed), true);
    TimeZone previous = TimeZone.getDefault();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    try {
      check("00:00".equals(ClockLayout.time(0, true, false, Locale.US)), "24-hour midnight");
      check("12:00:01".equals(ClockLayout.time(1000, false, true, Locale.US)), "12-hour seconds");
      check(
          "13:00:00".equals(ClockLayout.time(46800000, true, true, Locale.US)),
          "24-hour afternoon");
      check(
          "1:00".equals(ClockLayout.time(46800000, false, false, Locale.US)), "12-hour afternoon");
      check(
          "Thursday, January 1".equals(ClockLayout.date(0, Locale.US)),
          "actual local calendar date");
    } finally {
      TimeZone.setDefault(previous);
    }
    check(
        ClockLayout.step(-1) == 0 && ClockLayout.step(5000) == .1f && ClockLayout.step(33) > .032f,
        "bounded resume timing");
    for (float t = 0; t < 1000; t += .2f) {
      check(
          Math.abs(ClockLayout.driftX(t, .14f)) <= .14f
              && Math.abs(ClockLayout.driftY(t, .14f)) <= .14f,
          "clock stays in safe travel area");
      check(
          Math.abs(ClockLayout.driftX(t + .033f, .14f) - ClockLayout.driftX(t, .14f)) < .0002,
          "clock travel continuous");
    }
    System.out.println("Prism clock behavioral checks passed: " + checks);
  }
}
