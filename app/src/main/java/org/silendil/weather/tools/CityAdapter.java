package org.silendil.weather.tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.silendil.weather.City;

/**
 * Created by silendil on 28.02.2018.
 */

public abstract class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{

    private Context appContext;
    private City[] cityArray;

    public CityAdapter(Context appContext, City[] cityArray) {
        this.appContext = appContext;
        this.cityArray = cityArray;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(appContext),parent,cityArray){
            @Override
            public void onClick(View view) {
                getCityInfo(getLayoutPosition());
            }
        };
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cityArray != null ? cityArray.length:0;
    }

    public abstract void getCityInfo(int position);
}
