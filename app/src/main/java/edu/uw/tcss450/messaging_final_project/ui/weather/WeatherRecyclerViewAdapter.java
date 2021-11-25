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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.uw.tcss450.messaging_final_project.R;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {

    private Context context;
    private ArrayList<WeatherEntry> weatherEntryArrayList;

    public WeatherRecyclerViewAdapter(Context context, ArrayList<WeatherEntry> weatherEntryArrayList) {
        this.context = context;
        this.weatherEntryArrayList = weatherEntryArrayList;
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
        WeatherEntry modal = weatherEntryArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature() + "°F");
        Picasso.get().load("https://openweathermap.org/img/wn/".concat(modal.getIcon()).concat(".png")).into(holder.conditionIV);
        holder.windTV.setText(modal.getWindSpeed() + "Km/h");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try{
            Date t = input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherEntryArrayList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        public TextView windTV, temperatureTV, timeTV;
        public ImageView conditionIV;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemView.findViewById(R.id.idTVtemperature);
            timeTV = itemView.findViewById(R.id.idTVtime);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }

    void setWeather (final WeatherEntry weather) {

    }
}


