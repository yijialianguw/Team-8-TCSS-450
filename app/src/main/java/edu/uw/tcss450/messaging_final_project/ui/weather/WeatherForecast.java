package edu.uw.tcss450.messaging_final_project.ui.weather;

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

    public int getHour(){
        return this.hour;
    }


    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }


}
