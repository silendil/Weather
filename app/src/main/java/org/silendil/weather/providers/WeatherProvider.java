package org.silendil.weather.providers;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by phryts on 2/6/2018.
 */

public class WeatherProvider extends AbstractProvider{

    public WeatherProvider(String[] values) {
        super(values);
    }

    public String getInfo(String city){
        return get(city, "Wrong city");
    }

    public String[] getCitiesArray(){
        return getArray();
    }
}
