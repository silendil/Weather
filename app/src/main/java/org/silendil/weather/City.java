package org.silendil.weather;

import java.lang.reflect.Field;

/**
 * Created by silendil on 20.02.2018.
 */

public class City {

    private String cityName;
    private String weatherInformation;
    private int imageId;

    private static final String PACKAGE_NAME = "org.silendil.weather";

    public City(String cityName, String weatherInformation, int imageId){
        this.cityName = cityName;
        this.weatherInformation = weatherInformation;
        this.imageId = imageId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWeatherInformation() {
        return weatherInformation;
    }

    public int getImageId() {
        return imageId;
    }

    public static City[] createCityArray(String[] array){
        City[] result = new City[array.length];
        for(int i = 0; i < array.length;i++){
            String[] initial = array[i].split(":");
            result[i] = new City(initial[0], initial[1], getId(initial[2]));
        }
        return result;
    }

    private static int getId(String stringPath) {
        String className = stringPath.replace('.','$').substring(0,stringPath.lastIndexOf('.'));
        String fieldName = stringPath.substring(stringPath.lastIndexOf('.')+1);
        int result = 0;
        try {
            Class x = Class.forName(String.format("%s.%s",PACKAGE_NAME, className));
            Field res = x.getField(fieldName);
            if (res.getType() == int.class)
                result =  res.getInt(null);
        }catch (ClassNotFoundException ex){
            return 0;
        }catch (NoSuchFieldException ex){
            return 0;
        }catch (IllegalAccessException ex){
            return 0;
        }
        return result;
    }
}
