package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {

    private Context context;
    private ArrayList<WeatherForecast> weatherForecastArrayList;

    public WeatherRecyclerViewAdapter(Context context, ArrayList<WeatherForecast> weatherForecastArrayList) {
        this.context = context;
        this.weatherForecastArrayList = weatherForecastArrayList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_weather_rv_item,
                parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherForecast modal = weatherForecastArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperatureMax() + "°F");
        Picasso.get().load("https://openweathermap.org/img/wn/".concat(modal.getIcon()).concat(".png")).into(holder.conditionIV);
        holder.windTV.setText(modal.getWindSpeed() + "m/h");
    }

    @Override
    public int getItemCount() {
        return weatherForecastArrayList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        public TextView windTV, temperatureTV, dayTV;
        public ImageView conditionIV;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemView.findViewById(R.id.idTVtemperatureMax);
            dayTV = itemView.findViewById(R.id.idTVday);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }

    void setWeather (final WeatherForecast weather) {

    }
}


