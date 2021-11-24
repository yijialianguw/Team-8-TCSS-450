package edu.uw.tcss450.messaging_final_project.ui.weather;

public class WeatherForecast {

    private String day;
    private String temperatureMax;
    private String temperatureMin;
    private String icon;
    private String windSpeed;

    public WeatherForecast(String day, String temperatureMax, String temperatureMin,
                           String icon, String windSpeed){
        this.day = day;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
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

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

}
