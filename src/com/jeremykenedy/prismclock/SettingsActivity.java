package com.jeremykenedy.prismclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public final class SettingsActivity extends Activity {
  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    LinearLayout content = new LinearLayout(this);
    content.setOrientation(LinearLayout.VERTICAL);
    content.setPadding(56, 32, 56, 40);
    TextView title = new TextView(this);
    title.setText("Prism Clock");
    title.setTextSize(30);
    content.addView(title);
    TextView description = new TextView(this);
    description.setText(
        "Sculpted metal in continuous motion. Changes apply when the scene next starts.");
    description.setTextSize(17);
    content.addView(description);
    Button preview = new Button(this);
    preview.setText("Preview animation");
    preview.setFocusableInTouchMode(true);
    preview.setOnClickListener(
        new android.view.View.OnClickListener() {
          @Override
          public void onClick(android.view.View view) {
            startActivity(new Intent(SettingsActivity.this, PreviewActivity.class));
          }
        });
    content.addView(preview);
    for (String[] spec : PrismOptions.SCHEMA) {
      TextView label = new TextView(this);
      label.setText(spec[1]);
      label.setTextSize(19);
      label.setPadding(0, 18, 0, 4);
      content.addView(label);
      String[] choices = new String[spec.length - 2];
      System.arraycopy(spec, 3, choices, 0, spec.length - 3);
      choices[choices.length - 1] = "random";
      String[] labels = new String[choices.length];
      for (int i = 0; i < choices.length; i++) {
        String value = choices[i];
        if (value.equals("false")) labels[i] = "Off";
        else if (value.equals("true")) labels[i] = "On";
        else if (value.equals("12h")) labels[i] = "12-hour";
        else if (value.equals("24h")) labels[i] = "24-hour";
        else labels[i] = Character.toUpperCase(value.charAt(0)) + value.substring(1);
      }
      Spinner spinner = new Spinner(this);
      ArrayAdapter<String> adapter =
          new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);
      String current = prefs.getString(spec[0], spec[2]);
      for (int i = 0; i < choices.length; i++)
        if (choices[i].equals(current)) spinner.setSelection(i);
      spinner.setOnItemSelectedListener(
          new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                AdapterView<?> parent, android.view.View view, int position, long id) {
              prefs.edit().putString(spec[0], choices[position]).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
          });
      content.addView(spinner);
    }
    CheckBox random = new CheckBox(this);
    random.setText("Randomize all scene settings on start");
    random.setChecked(prefs.getBoolean("randomize_all", false));
    random.setOnCheckedChangeListener(
        new android.widget.CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(android.widget.CompoundButton button, boolean checked) {
            prefs.edit().putBoolean("randomize_all", checked).apply();
          }
        });
    content.addView(random);
    ScrollView scroll = new ScrollView(this);
    scroll.addView(content);
    setContentView(scroll);
    preview.requestFocus();
  }
}
