package org.silendil.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import static org.silendil.weather.ViewActivity.CITY_INFO;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String CITY = "CITY";
    private static int REQUEST_CODE = 0;
    private final static String SHARED_NAME = "LOCAL_FILE";
    private final static String STORED_INFO = "STORED_INFO";
    public final static String LOG_TAG = "WEATHER_APP";

    private TextView citiesInfo;
    private Spinner cities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra(CITY_INFO);
                SharedPreferences sf = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                StringBuilder stored = new StringBuilder(sf.getString(STORED_INFO,""));
                if(stored.length()!=0)
                    stored.append("\n");
                stored.append(result);
                sf.edit().putString(STORED_INFO,stored.toString()).apply();
                citiesInfo.setText(stored);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.get_info_button){
            String city = ((TextView)cities.getSelectedView()).getText().toString();
            Intent requestIntent = new Intent(MainActivity.this, ViewActivity.class);
            requestIntent.putExtra(CITY, city);
            getSharedPreferences(SHARED_NAME,MODE_PRIVATE).edit().putInt(getString(R.string.selectedPosition),cities.getSelectedItemPosition()).apply();
            startActivityForResult(requestIntent,REQUEST_CODE);
        }
        if(v.getId() == R.id.clear_history_button){
            getSharedPreferences(SHARED_NAME,MODE_PRIVATE).edit().putString(STORED_INFO,"").apply();
            citiesInfo.setText("");
        }
    }
}
