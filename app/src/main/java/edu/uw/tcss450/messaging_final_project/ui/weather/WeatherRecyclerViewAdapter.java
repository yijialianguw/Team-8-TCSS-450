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
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactCardBinding;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyViewHolder> {

    // creating variables for context and array list.
    private Context context;
    private ArrayList<WeatherForecast> weatherForecastArrayList;

    // creating a constructor
    public WeatherRecyclerViewAdapter(Context context, ArrayList<WeatherForecast> weatherForecastArrayList) {
        this.context = context;
        this.weatherForecastArrayList = weatherForecastArrayList;
    }

    /**public void setWeatherViewModel(WeatherViewModel mWeatherViewModel) {
        this.mWeatherViewModel = mWeatherViewModel;
    }*/

    @NonNull
    @Override
    public WeatherRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_rv_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeatherForecast weather = weatherForecastArrayList.get(position);
        holder.temperatureMaxTV.setText(weather.getTemperatureMax() + "°F");
        Picasso.get().load("https://openweathermap.org/img/wn/".concat(weather.getIcon()).concat(".png")).into(holder.conditionIV);
        holder.windTV.setText(weather.getWindSpeed() + "m/h");
    }

    @Override
    public int getItemCount() {
        return weatherForecastArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView conditionIV;
        private TextView windTV, temperatureMaxTV,  dayTV;
        private FragmentContactCardBinding binding;
        private View mItemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mItemView = itemView;
            // initializing our image view and text view.
            windTV = itemView.findViewById(R.id.idTVWindSpeedHour);
            temperatureMaxTV = itemView.findViewById(R.id.idTVtemperatureHour);
            dayTV = itemView.findViewById(R.id.idTVtime);
            conditionIV = itemView.findViewById(R.id.idIVConditionHour);
        }
    }

  void setWeather (final WeatherForecast weather) {

  }
}


