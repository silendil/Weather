package org.silendil.weather.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.silendil.weather.City;
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
    public final static String CITY_INDEX = "CITY_INDEX";
    private final static int VERTICAL = 1;

    private TextView citiesInfo;
    private ColorProvider colorProvider;


    private String backgroundColor = "#ffffff";
    private String captionColor = "#0000ff";
    private String historyColor = "#ff00ff";

    private City[] cityArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        colorProvider = new ColorProvider(getResources().getStringArray(R.array.colors));
        RecyclerView list = (RecyclerView)findViewById(R.id.list_view);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(VERTICAL);
        list.setLayoutManager(linear);
        list.setAdapter(new CityAdapter());
        citiesInfo = (TextView) findViewById(R.id.information_history);
        final WeatherProvider weatherProvider = new WeatherProvider(getResources().getStringArray(R.array.citisDictionary));
        cityArray = City.createCityArray(getResources().getStringArray(R.array.citisDictionary));
        Button clearInfo = (Button)findViewById(R.id.clear_history_button);
        clearInfo.setOnClickListener(this);
        SharedPreferences sf = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        citiesInfo.setText(sf.getString(STORED_INFO,""));
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

    private void getInfo(int position){
        Intent infoGet = new Intent(MainActivity.this,ViewActivity.class);
        infoGet.putExtra(CITY_INDEX, position);
        startActivityForResult(infoGet,REQUEST_CODE);
    }

    private class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{
        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CityViewHolder(LayoutInflater.from(getApplicationContext()),parent);
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return cityArray.length;
        }
    }

    private class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView icon;
        private TextView text;

        public CityViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item,parent,false));
            itemView.setOnClickListener(this);
            icon = (ImageView)itemView.findViewById(R.id.icon_item);
            text = (TextView)itemView.findViewById(R.id.text_item);
        }

        public void bind(int position){
            text.setText(cityArray[position].getCityName());
            icon.setImageResource(cityArray[position].getImageId());
        }

        @Override
        public void onClick(View view) {
            getInfo(getLayoutPosition());
        }
    }
}
