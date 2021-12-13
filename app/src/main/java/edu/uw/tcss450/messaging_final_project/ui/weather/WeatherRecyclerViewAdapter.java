package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.content.Context;
import android.util.Log;
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
import edu.uw.tcss450.messaging_final_project.databinding.FragmentWeatherRvDailyBinding;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyViewHolder> {

    // creating variables for context and array list.
    private ArrayList<WeatherForecast> weatherForecastArrayList;

    // creating a constructor
    public WeatherRecyclerViewAdapter(Context context, ArrayList<WeatherForecast> weatherForecastArrayList) {
        this.weatherForecastArrayList = weatherForecastArrayList;
        Log.e("WeatherRV", "contruct");
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
        holder.setWeatherForecast(weather);
    }

    @Override
    public int getItemCount() {
        return weatherForecastArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private WeatherForecast mWeatherForecast;
        private FragmentWeatherRvDailyBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = FragmentWeatherRvDailyBinding.bind(itemView);
        }

        private void setData(){
            binding.idTVtime.setText(mWeatherForecast.getHour()+"");
            binding.idTVtemperatureHour.setText(mWeatherForecast.getTemperature());
            Picasso.get().load("https://openweathermap.org/img/wn/".concat(mWeatherForecast.getIcon()).concat(".png")).into(binding.idIVConditionHour);
            binding.idTVWindSpeedHour.setText(mWeatherForecast.getWindSpeed());
        }

        public void setWeatherForecast(final WeatherForecast weatherForecast){
            this.mWeatherForecast = weatherForecast;
            setData();
        }

    }

}


