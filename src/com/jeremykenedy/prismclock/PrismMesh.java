package com.jeremykenedy.prismclock;

public final class PrismMesh {
  public static final int STRIDE = 11;

  private PrismMesh() {}

  public static float[] create(int columns) {
    if (columns < 4 || columns > 80)
      throw new IllegalArgumentException("Facet columns out of range");
    int rows = (int) Math.ceil(columns * .7);
    float step = 24f / columns;
    float dy = step * .8660254f;
    float[][][] nodes = new float[rows + 1][columns + 1][2];
    for (int row = 0; row <= rows; row++) {
      for (int col = 0; col <= columns; col++) {
        nodes[row][col][0] = (col - columns * .5f + (row % 2) * .5f) * step;
        nodes[row][col][1] = (row - rows * .5f) * dy;
      }
    }
    float[] mesh = new float[rows * columns * 6 * STRIDE];
    int index = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        float[] a = nodes[row][col],
            b = nodes[row][col + 1],
            c = nodes[row + 1][col],
            d = nodes[row + 1][col + 1];
        if (row % 2 == 0) {
          index = triangle(mesh, index, a[0], a[1], b[0], b[1], c[0], c[1]);
          index = triangle(mesh, index, b[0], b[1], d[0], d[1], c[0], c[1]);
        } else {
          index = triangle(mesh, index, a[0], a[1], b[0], b[1], d[0], d[1]);
          index = triangle(mesh, index, a[0], a[1], d[0], d[1], c[0], c[1]);
        }
      }
    }
    return mesh;
  }

  private static int triangle(
      float[] mesh, int index, float x0, float y0, float x1, float y1, float x2, float y2) {
    float[] corners = {x0, y0, x1, y1, x2, y2};
    for (int corner = 0; corner < 3; corner++) {
      mesh[index++] = corners[corner * 2];
      mesh[index++] = corners[corner * 2 + 1];
      for (float value : corners) mesh[index++] = value;
      for (int i = 0; i < 3; i++) mesh[index++] = corner == i ? 1f : 0f;
    }
    return index;
  }
}
