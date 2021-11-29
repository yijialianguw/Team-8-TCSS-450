package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Contains the weather information.
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel mWeatherViewModel;
    private UserInfoViewModel mUserModel;

    private TextView cityNameTV, temperatureRVTV, conditionTV, temperatureTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV;
    private ArrayList<WeatherForecast> weatherRVModelArrayList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    private String cityName;
    private double longitude;
    private double latitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latitude = 47.2452715310095;
        longitude = -122.43759901926524;
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        //Log.e("Weather", mUserModel.getmJwt());
        mWeatherViewModel = provider.get(WeatherViewModel.class);

        cityName = getCityName(longitude, latitude);
    }


    private String getCityName (double longitude, double latitude){
        String cityName = "Not found";
        Geocoder gcd = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);

            for(Address adr: addresses) {
                if(adr!=null){
                    String city = adr.getLocality();
                    if (city!=null && !city.equals("")){
                        cityName = city;
                    }else{
                        Log.d("TAG","CITY NOT FOUND");
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        // Inflate the layout for this fragment

        temperatureTV = view.findViewById(R.id.idTemp);
        conditionTV = view.findViewById(R.id.idIVCondition);
        weatherRV = view.findViewById(R.id.idRvWeather);
        iconIV = view.findViewById(R.id.idIVIcon);
        cityNameTV = view.findViewById(R.id.idTVCityName);

        weatherRVModelArrayList = new ArrayList<WeatherForecast>();
        weatherRV.setAdapter(weatherRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());

        mWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                JSONObject curr = response.getJSONObject("current");
                String temperature = curr.getString("temp");
                String condition  = curr.getJSONArray("weather").getJSONObject(0).getString("main");
                String conditionIcon   = curr.getJSONArray("weather").getJSONObject(0).getString("icon");

                cityNameTV.setText(cityName);
                temperatureTV.setText(temperature + "°F");

                Picasso.get().load("https://openweathermap.org/img/wn/".concat(conditionIcon).concat(".png")).into(iconIV);


                JSONObject forecastObj = response.getJSONObject("daily").getJSONObject(String.valueOf(0));
                JSONArray dayArray = forecastObj.getJSONArray("dt");
                for(int i =0; i < dayArray.length(); i++){
                    JSONObject dayObj = dayArray.getJSONObject(i);
                    String day = dayObj.getString("dt");
                    String tempMax = dayObj.getJSONArray("temp").getJSONObject(0).getString("max");
                    String tempMin = dayObj.getJSONArray("temp").getJSONObject(0).getString("min");
                    String img = dayObj.getJSONArray("weather").getJSONObject(0).getString("icon");
                    String wind = dayObj.getString("wind_speed");
                    weatherRVModelArrayList.add(new WeatherForecast(day, tempMax, tempMin, img, wind));
                }
                weatherRecyclerViewAdapter.notifyDataSetChanged();


            } catch(Exception e){ }
        });

        // TODO: add button that get weather data
        mWeatherViewModel.connect(String.valueOf(latitude), String.valueOf(longitude), mUserModel.getmJwt());

    }


}