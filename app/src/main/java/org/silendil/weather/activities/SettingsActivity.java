package org.silendil.weather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import org.silendil.weather.R;
import org.silendil.weather.providers.ColorProvider;

import static org.silendil.weather.activities.MainActivity.BACKGROUND;
import static org.silendil.weather.activities.MainActivity.CAPTION;
import static org.silendil.weather.activities.MainActivity.HISTORY;
import static org.silendil.weather.activities.MainActivity.SHARED_NAME;

/**
 * Created by silendil on 18.02.2018.
 */

public class SettingsActivity extends AppCompatActivity {

    private static final String BACK_CHECK = "BACKGROUND_CHECKBOX";
    private static final String CAPTION_CHECK = "CAPTION_CHECKBOX";
    private static final String HISTORY_CHECK = "HISTORY_CHECKBOX";

    private Spinner background;
    private Spinner caption;
    private Spinner history;

    SharedPreferences sf;

    private ColorProvider colorProvider;

    private int backgroundColorIndex;
    private int captionColorIndex;
    private int historyColorIndex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        init();
        Intent intent = getIntent();
        backgroundColorIndex = colorProvider.getIndex(intent.getStringExtra(BACKGROUND));
        captionColorIndex = colorProvider.getIndex(intent.getStringExtra(CAPTION));
        historyColorIndex = colorProvider.getIndex(intent.getStringExtra(HISTORY));
        setupCheckboxes();
    }

    private void setupCheckboxes(){
        sf = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        ((CheckBox)findViewById(R.id.back_color_choose)).setChecked(sf.getBoolean(BACK_CHECK,true));
        onChecked(findViewById(R.id.back_color_choose));
        ((CheckBox)findViewById(R.id.caption_color_choose)).setChecked(sf.getBoolean(CAPTION_CHECK,true));
        onChecked(findViewById(R.id.caption_color_choose));
        ((CheckBox)findViewById(R.id.history_color_choose)).setChecked(sf.getBoolean(HISTORY_CHECK,true));
        onChecked(findViewById(R.id.history_color_choose));
    }

    private void init(){
        colorProvider = new ColorProvider(getResources().getStringArray(R.array.colors));
        background = (Spinner)findViewById(R.id.back_color_chooser);
        caption = (Spinner)findViewById(R.id.caption_color_chooser);
        history = (Spinner)findViewById(R.id.history_color_chooser);
        background.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,colorProvider.getColorArray()));
        caption.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,colorProvider.getColorArray()));
        history.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,colorProvider.getColorArray()));
    }

    public void onChecked(View view){
        boolean checked = ((CheckBox)view).isChecked();
        int visibility = checked ? View.GONE : View.VISIBLE;
        switch (view.getId()){
            case R.id.back_color_choose:
                findViewById(R.id.settings_background_layout).setVisibility(visibility);
                if(!checked)
                    background.setSelection(backgroundColorIndex);
                sf.edit().putBoolean(BACK_CHECK,checked).apply();
                break;
            case R.id.caption_color_choose:
                findViewById(R.id.settings_caption_layout).setVisibility(visibility);
                if(!checked)
                    caption.setSelection(captionColorIndex);
                sf.edit().putBoolean(BACK_CHECK,checked).apply();
                break;
            case R.id.history_color_choose:
                findViewById(R.id.settings_history_layout).setVisibility(visibility);
                if(!checked)
                    history.setSelection(historyColorIndex);
                sf.edit().putBoolean(BACK_CHECK,checked).apply();
                break;
        }
    }

    public void onConfirmClick(View view){
        Intent result = new Intent();
        if(background.getSelectedView() != null)
            result.putExtra(BACKGROUND, colorProvider.getColor(((TextView)background.getSelectedView()).getText().toString()));
        if(caption.getSelectedView() != null)
            result.putExtra(CAPTION, colorProvider.getColor(((TextView)caption.getSelectedView()).getText().toString()));
        if(history.getSelectedView() != null)
            result.putExtra(HISTORY, colorProvider.getColor(((TextView)history.getSelectedView()).getText().toString()));
        setResult(RESULT_OK, result);
        finish();
    }
}
