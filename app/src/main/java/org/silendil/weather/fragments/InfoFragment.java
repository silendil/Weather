package org.silendil.weather.fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.silendil.weather.City;
import org.silendil.weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    private int position;

    private boolean cancelShow;

    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE_VALUE";

    private final static String DETAILS_FRAGMENT_TAG = "1a210f71-5c31-4d00-918a-e2236cba8b33";

    private TextView weatherInfo;
    private EditText messageText;
    private String weatherDetail;

    private Feedback feedback;


    public InfoFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_layout,container,false);
        initComponents(rootView);
        if(savedInstanceState != null)
            messageText.setText(savedInstanceState.getString(EXTRA_MESSAGE));
        FragmentManager manager = getChildFragmentManager();
        WeatherDetailsFragment weatherDetailsFragment = (WeatherDetailsFragment)manager.findFragmentByTag(DETAILS_FRAGMENT_TAG);
        if(weatherDetailsFragment == null){
            FragmentTransaction transaction = manager.beginTransaction();
            WeatherDetailsFragment details = new WeatherDetailsFragment();
            details.setDetailsMessage(weatherDetail);
            transaction.replace(R.id.info_details_fragment,details).commit();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_MESSAGE, messageText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        feedback = (Feedback)context;
        super.onAttach(context);
    }

    private void initComponents(View root){
        City[] cityArray = City.createCityArray(getResources().getStringArray(R.array.citisDictionary));
        ImageView icon = (ImageView)root.findViewById(R.id.city_icon);
        icon.setImageResource(cityArray[position].getImageId());
        weatherInfo = (TextView) root.findViewById(R.id.weather_info);
        String result = String.format("%s : %s",cityArray[position].getCityName(), cityArray[position].getWeatherInformation()) ;
        weatherInfo.setText(result);
        Button store = (Button) root.findViewById(R.id.store_button);
        store.setOnClickListener(this);
        Button send = (Button) root.findViewById(R.id.send_button);
        send.setOnClickListener(this);
        Button cancel = (Button) root.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
        if(!cancelShow)
            cancel.setVisibility(View.GONE);
        messageText = (EditText) root.findViewById(R.id.message_text);
        weatherDetail = cityArray[position].getDetails();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.store_button){
            feedback.saveHistory(weatherInfo.getText().toString());
        }
        if(v.getId() == R.id.cancel_button){
            feedback.cancel();
        }
        if(v.getId() == R.id.send_button){
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            String message;
            if (messageText.getText().toString().isEmpty())
                message = weatherInfo.getText().toString();
            else
                message = String.format("%s - (%s)",messageText.getText().toString(), weatherInfo.getText().toString());
            send.putExtra(Intent.EXTRA_TEXT, message);
            try {
                startActivity(send);
            }catch (ActivityNotFoundException ex){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.absent_software).setTitle(R.string.error);
                builder.create().show();
            }
        }
    }

    public interface Feedback{
        void saveHistory(String message);

        void cancel();
    }

    public void showCancelButton(boolean show){
        cancelShow = show;
    }
}
