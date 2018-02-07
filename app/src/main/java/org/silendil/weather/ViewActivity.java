package org.silendil.weather;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static org.silendil.weather.MainActivity.CITY;

/**
 * Created by phryts on 2/6/2018.
 */

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String CITY_INFO = "CITY_INFO";

    private TextView weatherInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);
        Intent request = getIntent();
        String city = request.getStringExtra(CITY);
        weatherInfo = (TextView) findViewById(R.id.weather_info);
        WeatherProvider provider = new WeatherProvider(getResources().getStringArray(R.array.citisDictionary));
        String result = city + " : " + provider.getInfo(city);
        weatherInfo.setText(result);
        Button store = (Button) findViewById(R.id.store_button);
        store.setOnClickListener(this);
        Button send = (Button) findViewById(R.id.send_button);
        send.setOnClickListener(this);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
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
            send.putExtra(CITY_INFO,weatherInfo.getText().toString());
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
