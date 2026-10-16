package com.jeremykenedy.prismclock;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

final class PrismRenderer implements GLSurfaceView.Renderer {
  private final Context context;
  private final PrismOptions options;
  private final FloatBuffer vertices;
  private final int count;
  private final float[] projection = new float[16], view = new float[16], matrix = new float[16];
  private static final float[][] PALETTES = {
    {.64f, .72f, .85f}, {.18f, .23f, .32f}, {.87f, .65f, .35f}, {.4f, .3f, .72f}
  };
  private int program,
      vertexBuffer,
      matrixLocation,
      timeLocation,
      depthLocation,
      brightnessLocation,
      metalLocation,
      finishLocation;
  private final int[] attributes = new int[5];
  volatile float seconds;

  PrismRenderer(Context context, PrismOptions options) {
    this.context = context;
    this.options = options;
    float[] mesh = PrismMesh.create(options.columns);
    count = mesh.length / PrismMesh.STRIDE;
    vertices =
        ByteBuffer.allocateDirect(mesh.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    vertices.put(mesh).position(0);
  }

  private String source(int id) {
    try (InputStream input = context.getResources().openRawResource(id);
        ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[2048];
      int length;
      while ((length = input.read(buffer)) != -1) output.write(buffer, 0, length);
      return output.toString("UTF-8");
    } catch (Exception error) {
      throw new IllegalStateException("Cannot read surface shader", error);
    }
  }

  private int shader(int type, String text) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, text);
    GLES20.glCompileShader(shader);
    int[] status = new int[1];
    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);
    if (status[0] == 0) throw new IllegalStateException(GLES20.glGetShaderInfoLog(shader));
    return shader;
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    int vertex = shader(GLES20.GL_VERTEX_SHADER, source(R.raw.prism_vertex));
    String fragmentSource = source(R.raw.prism_fragment);
    String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
    if (extensions != null && extensions.contains("GL_OES_standard_derivatives")) {
      fragmentSource =
          "#extension GL_OES_standard_derivatives : enable\n#define PRISM_DERIVATIVES\n"
              + fragmentSource;
    }
    int fragment = shader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
    program = GLES20.glCreateProgram();
    GLES20.glAttachShader(program, vertex);
    GLES20.glAttachShader(program, fragment);
    GLES20.glLinkProgram(program);
    int[] status = new int[1];
    GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0);
    GLES20.glDeleteShader(vertex);
    GLES20.glDeleteShader(fragment);
    if (status[0] == 0) throw new IllegalStateException(GLES20.glGetProgramInfoLog(program));
    int[] buffers = new int[1];
    GLES20.glGenBuffers(1, buffers, 0);
    vertexBuffer = buffers[0];
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer);
    vertices.position(0);
    GLES20.glBufferData(
        GLES20.GL_ARRAY_BUFFER, vertices.capacity() * 4, vertices, GLES20.GL_STATIC_DRAW);
    String[] names = {"aPosition", "aCorner0", "aCorner1", "aCorner2", "aBarycentric"};
    for (int i = 0; i < names.length; i++)
      attributes[i] = GLES20.glGetAttribLocation(program, names[i]);
    matrixLocation = GLES20.glGetUniformLocation(program, "uMatrix");
    timeLocation = GLES20.glGetUniformLocation(program, "uTime");
    depthLocation = GLES20.glGetUniformLocation(program, "uDepth");
    brightnessLocation = GLES20.glGetUniformLocation(program, "uBrightness");
    metalLocation = GLES20.glGetUniformLocation(program, "uMetal");
    finishLocation = GLES20.glGetUniformLocation(program, "uFinish");
    GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    GLES20.glClearColor(.02f, .025f, .04f, 1);
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES20.glViewport(0, 0, width, height);
    Matrix.perspectiveM(projection, 0, 53, width / (float) Math.max(1, height), 1, 40);
    Matrix.setLookAtM(view, 0, 0, 0, 11.8f, 0, 0, 0, 0, 1, 0);
    Matrix.multiplyMM(matrix, 0, projection, 0, view, 0);
  }

  private void attribute(int index, int size, int offset) {
    int location = attributes[index];
    GLES20.glEnableVertexAttribArray(location);
    GLES20.glVertexAttribPointer(
        location, size, GLES20.GL_FLOAT, false, PrismMesh.STRIDE * 4, offset * 4);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    GLES20.glUseProgram(program);
    GLES20.glUniformMatrix4fv(matrixLocation, 1, false, matrix, 0);
    GLES20.glUniform1f(timeLocation, seconds * options.speed);
    GLES20.glUniform1f(depthLocation, options.depth * 2.7f);
    GLES20.glUniform1f(brightnessLocation, options.brightness);
    float[] metal = PALETTES[options.palette];
    GLES20.glUniform3fv(metalLocation, 1, metal, 0);
    GLES20.glUniform1f(finishLocation, options.palette == 3 ? 1f : 0f);
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer);
    attribute(0, 2, 0);
    attribute(1, 2, 2);
    attribute(2, 2, 4);
    attribute(3, 2, 6);
    attribute(4, 3, 8);
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, count);
  }
}
