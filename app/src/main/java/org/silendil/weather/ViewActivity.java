package org.silendil.weather;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static org.silendil.weather.MainActivity.LOG_TAG;

import static org.silendil.weather.MainActivity.CITY;

/**
 * Created by phryts on 2/6/2018.
 */

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String CITY_INFO = "CITY_INFO";
    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE_VALUE";

    private TextView weatherInfo;
    private EditText messageText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);
        initComponents();
        if(savedInstanceState != null)
            messageText.setText(savedInstanceState.getString(EXTRA_MESSAGE));
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

    private void initComponents(){
        Intent request = getIntent();
        String city = request.getStringExtra(CITY);
        weatherInfo = (TextView) findViewById(R.id.weather_info);
        WeatherProvider provider = new WeatherProvider(getResources().getStringArray(R.array.citisDictionary));
        String result = String.format("%s : %s",city, provider.getInfo(city)) ;
        weatherInfo.setText(result);
        Button store = (Button) findViewById(R.id.store_button);
        store.setOnClickListener(this);
        Button send = (Button) findViewById(R.id.send_button);
        send.setOnClickListener(this);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
        messageText = (EditText) findViewById(R.id.message_text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_MESSAGE, messageText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.store_button){
            Intent result = new Intent();
            result.putExtra(CITY_INFO,weatherInfo.getText().toString());
            setResult(RESULT_OK,result);
            finish();
        }
        if(v.getId() == R.id.cancel_button){
            setResult(RESULT_CANCELED);
            finish();
        }
        if(v.getId() == R.id.send_button){
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            String message = String.format("%s - (%s)",messageText.getText().toString(), weatherInfo.getText().toString());
            send.putExtra(Intent.EXTRA_TEXT, message);
            try {
                startActivity(send);
            }catch (ActivityNotFoundException ex){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.absent_software).setTitle(R.string.error);
                builder.create().show();
            }
        }
    }
}
