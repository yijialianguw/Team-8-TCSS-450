package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
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
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel mWeatherViewModel;
    private UserInfoViewModel mUserModel;

    private TextView cityNameTV, temperatureTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV;
    private ArrayList<WeatherViewModel> weatherRVModelArrayList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    private String cityName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        //Log.e("Weather", mUserModel.getmJwt());
        mWeatherViewModel = provider.get(WeatherViewModel.class);

       // cityName = getCityName(location.getLongitude(), location.getLatitude());

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

        temperatureTV = view.findViewById(R.id.idTVtemperature);
        conditionTV = view.findViewById(R.id.idIVCondition);
        weatherRV = view.findViewById(R.id.idRvWeather);
        iconIV = view.findViewById(R.id.idIVIcon);
        cityNameTV = view.findViewById(R.id.idTVCityName);

        weatherRVModelArrayList = new ArrayList<>();
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
                String str = "Temperature: " + curr.getString("temp") + "\nSunrise: " + curr.getString("sunrise") + "\nSunset: " + curr.getString("sunset")+ "\nWeather: " + curr.getJSONArray("weather").getJSONObject(0).getString("main");
                //binding.editWeather.setText(str);
            } catch(Exception e){

            }
        });

        // TODO: add button that get weather data
        String LAT = "10";
        String LON = "10";
        mWeatherViewModel.connect(LAT, LON, mUserModel.getmJwt());

    }


}