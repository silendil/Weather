package org.silendil.weather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.silendil.weather.R;
import org.silendil.weather.fragments.InfoFragment;

import static org.silendil.weather.activities.MainActivity.CITY_INDEX;

/**
 * Created by phryts on 2/6/2018.
 */

public class ViewActivity extends AppCompatActivity implements InfoFragment.Feedback{

    public final static String CITY_INFO = "CITY_INFO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setPosition(getIntent().getIntExtra(CITY_INDEX,0));
        infoFragment.showCancelButton(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.info_fragment,infoFragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        Log.d(MainActivity.LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(MainActivity.LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(MainActivity.LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(MainActivity.LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(MainActivity.LOG_TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(MainActivity.LOG_TAG, "onDestroy");
        super.onDestroy();
    }


    @Override
    public void saveHistory(String message) {
        Intent result = new Intent();
        result.putExtra(CITY_INFO, message);
        setResult(RESULT_OK,result);
        finish();
    }

    @Override
    public void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
