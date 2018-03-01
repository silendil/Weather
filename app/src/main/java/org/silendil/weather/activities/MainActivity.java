package org.silendil.weather.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.silendil.weather.R;
import org.silendil.weather.fragments.InfoFragment;
import org.silendil.weather.fragments.ListCityFragment;


public class MainActivity extends AppCompatActivity implements ListCityFragment.CityInformer, InfoFragment.Feedback{

    private static final int REQUEST_CODE = 0;
    public static final int SETTINGS_CODE = 1;
    public final static String SHARED_NAME = "LOCAL_FILE";

    public final static String LOG_TAG = "WEATHER_APP";
    public final static String BACKGROUND = "BACKGROUND_COLOR";
    public final static String CAPTION = "CAPTION_COLOR";
    public final static String HISTORY = "HISTORY_COLOR";
    public final static String CITY_INDEX = "CITY_INDEX";

    private ListCityFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listFragment = new ListCityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.list_fragment,listFragment);
        transaction.commit();
    }

    @Override
    public void viewInfo(int position) {
        if(findViewById(R.id.info_fragment) == null){
            Intent infoGet = new Intent(MainActivity.this,ViewActivity.class);
            infoGet.putExtra(CITY_INDEX, position);
            startActivityForResult(infoGet,REQUEST_CODE);
        }else{
            InfoFragment infoFragment = new InfoFragment();
            infoFragment.setPosition(position);
            infoFragment.showCancelButton(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.info_fragment,infoFragment);
            transaction.commit();
        }
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
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    String result = data.getStringExtra(ViewActivity.CITY_INFO);
                    listFragment.addHistory(result);
                }
                break;
            case SETTINGS_CODE:
                if(resultCode == RESULT_OK){
                    listFragment.applySettings(data,listFragment.getView());
                }
                break;
        }
    }

    @Override
    public void saveHistory(String message) {
        listFragment.addHistory(message);
    }

    @Override
    public void cancel() {

    }
}
