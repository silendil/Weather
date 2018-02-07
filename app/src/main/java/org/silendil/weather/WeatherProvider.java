package org.silendil.weather;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by phryts on 2/6/2018.
 */

public class WeatherProvider {
    private Map<String, String> dictionary = new TreeMap<>();

    public WeatherProvider(String[] dict){
        for(String item : dict){
            dictionary.put(item.split(":")[0],item.split(":")[1]);
        }
    }

    public String getInfo(String city){
        if(dictionary.containsKey(city))
            return dictionary.get(city);
        return "Wrong city";
    }

    public String[] getCitiesArray(){
        if(dictionary != null) {
            Collection<String> cities = dictionary.keySet();
            return cities.toArray(new String[cities.size()]);
        }
        return null;
    }
}
