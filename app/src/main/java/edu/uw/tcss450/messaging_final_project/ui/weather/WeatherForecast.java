package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class WeatherForecast {

    private String temperature;
    private String icon;
    private String windSpeed;
    private int hour;

    public WeatherForecast(int hour, String temperature,
                           String icon, String windSpeed){
        this.hour = hour;
        this.temperature = temperature;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

    public int getHourly(){
        return getTimeHour(this.hour);
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

    public int getTimeHour(int epoch) {
        Date date1 = new Date (Integer.valueOf(epoch)*1000);
        Calendar c = Calendar.getInstance();
        c.setTime(date1);

        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        return hourOfDay;
    }

}