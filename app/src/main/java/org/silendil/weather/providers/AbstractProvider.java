package org.silendil.weather.providers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by silendil on 18.02.2018.
 */

public class AbstractProvider {
    private Map<String, String> dictionary = new TreeMap<>();

    public AbstractProvider(){
    }

    public AbstractProvider(String[] values){
        for(String item : values){
            dictionary.put(item.split(":")[0],item.split(":")[1]);
        }
    }

    public String get(String key, String defValue){
        if(dictionary.containsKey(key))
            return dictionary.get(key);
        return defValue;
    }

    public String[] getArray(){
        if(dictionary != null) {
            Collection<String> cities = dictionary.keySet();
            return cities.toArray(new String[cities.size()]);
        }
        return null;
    }

    public String getKey(String value){
        for(Map.Entry<String,String> entry : dictionary.entrySet()){
            if(entry.getValue().equals(value))
                return entry.getKey();
        }
        return null;
    }

    public int getIndex(String key){
        String[] array = getArray();
        for(int i = 0; i < array.length; i++){
            if(array[i].equals(key))
                return i;
        }
        return -1;
    }
}
