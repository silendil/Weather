package org.silendil.weather.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.silendil.weather.City;
import org.silendil.weather.R;
import org.silendil.weather.activities.SettingsActivity;
import org.silendil.weather.providers.ColorProvider;
import org.silendil.weather.tools.CityAdapter;

import static android.content.Context.MODE_PRIVATE;
import static org.silendil.weather.activities.MainActivity.SHARED_NAME;
import static org.silendil.weather.activities.MainActivity.SETTINGS_CODE;
import static org.silendil.weather.activities.MainActivity.BACKGROUND;
import static org.silendil.weather.activities.MainActivity.CAPTION;
import static org.silendil.weather.activities.MainActivity.HISTORY;


public class ListCityFragment extends Fragment implements View.OnClickListener {

    private final static String STORED_INFO = "STORED_INFO";

    private final static int VERTICAL = 1;

    private TextView citiesInfo;
    private ColorProvider colorProvider;


    private String backgroundColor = "#ffffff";
    private String captionColor = "#0000ff";
    private String historyColor = "#ff00ff";

    private CityInformer informer;

    public ListCityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View root) {
        colorProvider = new ColorProvider(getResources().getStringArray(R.array.colors));
        City[] cityArray = City.createCityArray(getResources().getStringArray(R.array.citisDictionary));
        RecyclerView list = (RecyclerView) root.findViewById(R.id.list_view);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        linear.setOrientation(VERTICAL);
        list.setLayoutManager(linear);
        list.setAdapter(new CityAdapter(getContext(), cityArray) {
            @Override
            public void getCityInfo(int position) {
                informer.viewInfo(position);
            }
        });
        citiesInfo = (TextView) root.findViewById(R.id.information_history);
        Button clearInfo = (Button) root.findViewById(R.id.clear_history_button);
        ImageButton settings = (ImageButton) root.findViewById(R.id.settings_button);
        clearInfo.setOnClickListener(this);
        settings.setOnClickListener(this);
        SharedPreferences sf = getContext().getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        citiesInfo.setText(sf.getString(STORED_INFO, ""));
        backgroundColor = sf.getString(BACKGROUND, "#ffffff");
        captionColor = sf.getString(CAPTION, "#0000ff");
        historyColor = sf.getString(HISTORY, "#ff00ff");
        setColors(root);
    }

    private void setColors(View root) {
        ((TextView) root.findViewById(R.id.caption)).setTextColor(Color.parseColor(captionColor));
        ((TextView) root.findViewById(R.id.usage_history)).setTextColor(Color.parseColor(captionColor));
        ((TextView) root.findViewById(R.id.information_history)).setTextColor(Color.parseColor(historyColor));
        root.findViewById(R.id.main_layout).setBackgroundColor(Color.parseColor(backgroundColor));
    }

    @Override
    public void onAttach(Context context) {
        informer = (CityInformer) context;
        super.onAttach(context);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_history_button:
                getContext().getSharedPreferences(SHARED_NAME, MODE_PRIVATE).edit().putString(STORED_INFO, "").apply();
                citiesInfo.setText("");
                break;
            case R.id.settings_button:
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                settingsIntent.putExtra(BACKGROUND, colorProvider.getKey(backgroundColor));
                settingsIntent.putExtra(CAPTION, colorProvider.getKey(captionColor));
                settingsIntent.putExtra(HISTORY, colorProvider.getKey(historyColor));
                ((AppCompatActivity)getContext()).startActivityForResult(settingsIntent, SETTINGS_CODE);
                break;
        }
    }

    public void applySettings(Intent data, View root) {
        SharedPreferences sf = getContext().getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        if (data.getStringExtra(BACKGROUND) != null) {
            backgroundColor = data.getStringExtra(BACKGROUND);
            sf.edit().putString(BACKGROUND, backgroundColor).apply();
        }
        if (data.getStringExtra(CAPTION) != null) {
            captionColor = data.getStringExtra(CAPTION);
            sf.edit().putString(CAPTION, captionColor).apply();
        }
        if (data.getStringExtra(HISTORY) != null) {
            historyColor = data.getStringExtra(HISTORY);
            sf.edit().putString(HISTORY, historyColor).apply();
        }
        setColors(root);
    }

    public void addHistory(String message) {
        SharedPreferences sf = getContext().getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        StringBuilder stored = new StringBuilder(sf.getString(STORED_INFO, ""));
        if (stored.length() != 0)
            stored.append("\n");
        stored.append(message);
        sf.edit().putString(STORED_INFO, stored.toString()).apply();
        citiesInfo.setText(stored);
    }

    public interface CityInformer {
        void viewInfo(int position);
    }
}
