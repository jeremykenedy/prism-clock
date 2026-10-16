package com.jeremykenedy.prismclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.FrameLayout;
import java.util.Locale;
import java.util.Random;

public final class PrismSceneView extends FrameLayout {
  private final GLSurfaceView surface;
  private final PrismRenderer renderer;
  private final ClockFace clock;
  private final Handler handler = new Handler(Looper.getMainLooper());
  private boolean running;
  private long previous;
  private final Runnable tick =
      new Runnable() {
        @Override
        public void run() {
          if (!running) return;
          long now = SystemClock.uptimeMillis();
          renderer.seconds += ClockLayout.step(now - previous);
          previous = now;
          surface.requestRender();
          clock.invalidate();
          handler.postDelayed(this, 33);
        }
      };

  public PrismSceneView(Context context) {
    super(context);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    String[] values = new String[PrismOptions.SCHEMA.length];
    for (int i = 0; i < values.length; i++)
      values[i] = prefs.getString(PrismOptions.SCHEMA[i][0], PrismOptions.SCHEMA[i][2]);
    PrismOptions options =
        new PrismOptions(values, new Random(), prefs.getBoolean("randomize_all", false));
    surface = new GLSurfaceView(context);
    surface.setEGLContextClientVersion(2);
    surface.setEGLConfigChooser(8, 8, 8, 0, 0, 0);
    renderer = new PrismRenderer(context, options);
    surface.setRenderer(renderer);
    surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    addView(surface, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    clock = new ClockFace(context, options);
    addView(clock, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
  }

  public void start() {
    if (running) return;
    running = true;
    previous = SystemClock.uptimeMillis();
    surface.onResume();
    handler.post(tick);
  }

  public void stop() {
    if (!running) return;
    running = false;
    handler.removeCallbacks(tick);
    surface.onPause();
  }

  public void release() {
    stop();
  }

  @Override
  protected void onDetachedFromWindow() {
    stop();
    super.onDetachedFromWindow();
  }

  private final class ClockFace extends View {
    private final PrismOptions options;
    private final Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);

    ClockFace(Context context, PrismOptions options) {
      super(context);
      this.options = options;
      text.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
      text.setTextAlign(Paint.Align.CENTER);
      text.setColor(Color.rgb(248, 249, 253));
      setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      float width = getWidth(), height = getHeight(), scale = height / 1080f;
      float x = width * (.5f + ClockLayout.driftX(renderer.seconds, options.travel));
      float y = height * (.5f + ClockLayout.driftY(renderer.seconds, options.travel));
      text.setShadowLayer(18 * scale, 0, 3 * scale, 0xc9000000);
      text.setTextSize((options.seconds ? 142 : 184) * scale * options.clockScale);
      long now = System.currentTimeMillis();
      Locale locale = Locale.getDefault();
      String time = ClockLayout.time(now, options.use24Hour, options.seconds, locale);
      float baseline = y - (text.ascent() + text.descent()) * .5f;
      canvas.drawText(time, x, baseline, text);
      text.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
      text.setTextSize(24 * scale * options.clockScale);
      if (!options.use24Hour) {
        String period = new java.text.SimpleDateFormat("a", locale).format(new java.util.Date(now));
        canvas.drawText(period, x, y + height * .108f, text);
      }
      if (options.date)
        canvas.drawText(
            ClockLayout.date(now, locale), x, y + height * (options.use24Hour ? .12f : .16f), text);
      text.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
    }
  }
}
