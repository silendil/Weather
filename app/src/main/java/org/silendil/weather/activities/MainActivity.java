package org.silendil.weather.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.silendil.weather.R;
import org.silendil.weather.providers.ColorProvider;
import org.silendil.weather.providers.WeatherProvider;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String CITY = "CITY";
    private static final int REQUEST_CODE = 0;
    private static final int SETTINGS_CODE = 1;
    public final static String SHARED_NAME = "LOCAL_FILE";
    private final static String STORED_INFO = "STORED_INFO";

    public final static String LOG_TAG = "WEATHER_APP";
    public final static String BACKGROUND = "BACKGROUND_COLOR";
    public final static String CAPTION = "CAPTION_COLOR";
    public final static String HISTORY = "HISTORY_COLOR";

    private TextView citiesInfo;
    private Spinner cities;
    private ColorProvider colorProvider;

    private String backgroundColor = "#ffffff";
    private String captionColor = "#0000ff";
    private String historyColor = "#ff00ff";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        colorProvider = new ColorProvider(getResources().getStringArray(R.array.colors));
        cities = (Spinner) findViewById(R.id.cities);
        citiesInfo = (TextView) findViewById(R.id.information_history);
        final WeatherProvider weatherProvider = new WeatherProvider(getResources().getStringArray(R.array.citisDictionary));
        cities.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,weatherProvider.getCitiesArray()));
        cities.setSelection(getPreferences(Context.MODE_PRIVATE).getInt(getString(R.string.selectedPosition),0),false);
        Button getInfo = (Button)findViewById(R.id.get_info_button);
        getInfo.setOnClickListener(this);
        Button clearInfo = (Button)findViewById(R.id.clear_history_button);
        clearInfo.setOnClickListener(this);
        SharedPreferences sf = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        citiesInfo.setText(sf.getString(STORED_INFO,""));
        cities.setSelection(sf.getInt(getString(R.string.selectedPosition),0));
        backgroundColor = sf.getString(BACKGROUND,"#ffffff");
        captionColor = sf.getString(CAPTION,"#0000ff");
        historyColor = sf.getString(HISTORY, "#ff00ff");
        setColors();
    }

    private void setColors(){
        ((TextView)findViewById(R.id.caption)).setTextColor(Color.parseColor(captionColor));
        ((TextView)findViewById(R.id.usage_history)).setTextColor(Color.parseColor(captionColor));
        ((TextView)findViewById(R.id.information_history)).setTextColor(Color.parseColor(historyColor));
        findViewById(R.id.main_layout).setBackgroundColor(Color.parseColor(backgroundColor));
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(LOG_TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sf = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra(ViewActivity.CITY_INFO);
                    StringBuilder stored = new StringBuilder(sf.getString(STORED_INFO,""));
                    if(stored.length()!=0)
                        stored.append("\n");
                    stored.append(result);
                    sf.edit().putString(STORED_INFO,stored.toString()).apply();
                    citiesInfo.setText(stored);
                }
                break;
            case SETTINGS_CODE:
                if(resultCode == RESULT_OK){
                    if(data.getStringExtra(BACKGROUND) != null) {
                        backgroundColor = data.getStringExtra(BACKGROUND);
                        sf.edit().putString(BACKGROUND,backgroundColor).apply();
                    }
                    if(data.getStringExtra(CAPTION) != null) {
                        captionColor = data.getStringExtra(CAPTION);
                        sf.edit().putString(CAPTION,captionColor).apply();
                    }
                    if(data.getStringExtra(HISTORY) != null) {
                        historyColor = data.getStringExtra(HISTORY);
                        sf.edit().putString(HISTORY,historyColor).apply();
                    }
                    setColors();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.get_info_button:
                String city = ((TextView)cities.getSelectedView()).getText().toString();
                Intent requestIntent = new Intent(this, ViewActivity.class);
                requestIntent.putExtra(CITY, city);
                getSharedPreferences(SHARED_NAME,MODE_PRIVATE).edit().putInt(getString(R.string.selectedPosition),cities.getSelectedItemPosition()).apply();
                startActivityForResult(requestIntent,REQUEST_CODE);
                break;
            case R.id.clear_history_button:
                getSharedPreferences(SHARED_NAME,MODE_PRIVATE).edit().putString(STORED_INFO,"").apply();
                citiesInfo.setText("");
                break;
            case R.id.settings_button:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra(BACKGROUND, colorProvider.getKey(backgroundColor));
                settingsIntent.putExtra(CAPTION, colorProvider.getKey(captionColor));
                settingsIntent.putExtra(HISTORY, colorProvider.getKey(historyColor));
                startActivityForResult(settingsIntent,SETTINGS_CODE);
                break;
        }
    }
}
