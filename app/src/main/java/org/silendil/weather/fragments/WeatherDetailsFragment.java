package org.silendil.weather.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.silendil.weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailsFragment extends Fragment implements View.OnClickListener {

    private String detailsMessage;

    private TextView details;

    public void setDetailsMessage(String detailsMessage) {
        this.detailsMessage = detailsMessage;
    }

    public WeatherDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.info_details_layout, container,false);
        root.findViewById(R.id.show_weather_details).setOnClickListener(this);
        details = (TextView)root.findViewById(R.id.weather_details);
        details.setText(detailsMessage);
        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.show_weather_details){
            if(((CheckBox)view).isChecked()){
                details.setVisibility(View.VISIBLE);
            }else{
                details.setVisibility(View.GONE);
            }
        }
    }
}
