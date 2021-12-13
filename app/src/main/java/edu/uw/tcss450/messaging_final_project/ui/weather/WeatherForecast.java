package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class WeatherForecast {

    private String temperature;
    private String icon;
    private String windSpeed;
    private String hour;

    public WeatherForecast(String hour, String temperature,
                           String icon, String windSpeed){
        this.hour = hour;
        this.temperature = temperature;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

    public String getHourly(){
        return this.hour;
    }


    public String getTemperature() {
        return String.valueOf(Math.round(Float.parseFloat(temperature)));
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return String.valueOf(Math.round(Float.parseFloat(windSpeed)));
    }


}