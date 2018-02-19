package org.silendil.weather.providers;

/**
 * Created by silendil on 18.02.2018.
 */

public class ColorProvider extends AbstractProvider {

    public ColorProvider(String[] values) {
        super(values);
    }

    public String getColor(String colorName){
        return get(colorName, "Wrong color");
    }

    public String[] getColorArray(){
        return getArray();
    }
}
