package org.silendil.weather.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.silendil.weather.City;
import org.silendil.weather.R;

/**
 * Created by silendil on 28.02.2018.
 */

public abstract class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView icon;
    private TextView text;
    private City[] cityArray;

    public CityViewHolder(LayoutInflater inflater, ViewGroup parent, City[] cityArray) {
        super(inflater.inflate(R.layout.list_item,parent,false));
        this.cityArray = cityArray;
        itemView.setOnClickListener(this);
        icon = (ImageView)itemView.findViewById(R.id.icon_item);
        text = (TextView)itemView.findViewById(R.id.text_item);
    }

    public void bind(int position){
        text.setText(cityArray[position].getCityName());
        icon.setImageResource(cityArray[position].getImageId());
    }
}
