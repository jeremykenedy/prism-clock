package com.jeremykenedy.prismclock;

import java.util.Random;

public final class PrismOptions {
  public static final String[][] SCHEMA = {
    {"palette", "Metal finish", "silver", "silver", "graphite", "champagne", "iridescent"},
    {"speed", "Surface motion", "gentle", "slow", "gentle", "flowing"},
    {"facets", "Facet size", "medium", "large", "medium", "small"},
    {"depth", "Surface depth", "sculpted", "subtle", "sculpted", "dramatic"},
    {"brightness", "Lighting", "balanced", "dim", "balanced", "bright"},
    {"clock_size", "Clock size", "standard", "subtle", "standard", "large"},
    {"clock_drift", "Clock travel", "gentle", "gentle", "wide"},
    {"clock_format", "Time format", "24h", "12h", "24h"},
    {"show_seconds", "Seconds", "false", "false", "true"},
    {"show_date", "Date", "true", "false", "true"}
  };
  public final int palette, columns;
  public final float speed, depth, brightness, clockScale, travel;
  public final boolean use24Hour, seconds, date;

  public PrismOptions(String[] values, Random random, boolean randomize) {
    int[] choice = new int[SCHEMA.length];
    for (int i = 0; i < choice.length; i++) {
      String selected = randomize ? "random" : values[i];
      choice[i] = index(i, selected, random);
    }
    palette = choice[0];
    speed = new float[] {.18f, .34f, .6f}[choice[1]];
    columns = new int[] {24, 36, 52}[choice[2]];
    depth = new float[] {.16f, .36f, .62f}[choice[3]];
    brightness = new float[] {.62f, .9f, 1.15f}[choice[4]];
    clockScale = new float[] {.76f, 1f, 1.25f}[choice[5]];
    travel = choice[6] == 0 ? .035f : .14f;
    use24Hour = choice[7] == 1;
    seconds = choice[8] == 1;
    date = choice[9] == 1;
  }

  private static int index(int row, String value, Random random) {
    String[] spec = SCHEMA[row];
    if ("random".equals(value)) return random.nextInt(spec.length - 3);
    for (int i = 3; i < spec.length; i++) if (spec[i].equals(value)) return i - 3;
    return java.util.Arrays.asList(spec).subList(3, spec.length).indexOf(spec[2]);
  }

  public static int row(String key) {
    for (int i = 0; i < SCHEMA.length; i++) if (SCHEMA[i][0].equals(key)) return i;
    return -1;
  }

  public static boolean supported(String key, String value) {
    if ("randomize_all".equals(key) || "show_clock".equals(key))
      return "true".equals(value) || "false".equals(value);
    int row = row(key);
    if (row < 0 || value == null) return false;
    if ("random".equals(value)) return true;
    for (int i = 3; i < SCHEMA[row].length; i++) if (SCHEMA[row][i].equals(value)) return true;
    return false;
  }
}
