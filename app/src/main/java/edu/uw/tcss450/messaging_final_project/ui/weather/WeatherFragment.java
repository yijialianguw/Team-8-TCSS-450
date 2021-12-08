package edu.uw.tcss450.messaging_final_project.ui.weather;

import static java.sql.DriverManager.println;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Date;
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

    private TextView cityNameTV, temperatureRVTV, conditionTV, temperatureTV, conditionRVTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV, iconIV1, iconIV2, iconIV3, iconIV4, iconIV5;
    private ArrayList<WeatherForecast> weatherRVModelArrayList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    private String cityName;
    private double longitude;
    private double latitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latitude = 46.855795284745774;
        longitude = -121.7190505567243;
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mWeatherViewModel = provider.get(WeatherViewModel.class);
        cityName = getCityName(longitude, latitude);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        // Inflate the layout for this fragment
       // temperatureTV = view.findViewById(R.id.idTemp);
       // conditionRVTV = view.findViewById(R.id.idIVCondition);
       // conditionTV = view.findViewById(R.id.idTVCondition);
       // weatherRV = view.findViewById(R.id.idRvWeather);
        iconIV = view.findViewById(R.id.idIVIcon);
        iconIV1 = view.findViewById(R.id.idIVIconDay1);
        iconIV2 = view.findViewById(R.id.idIVIconDay2);
        iconIV3 = view.findViewById(R.id.idIVIconDay3);
        iconIV4 = view.findViewById(R.id.idIVIconDay4);
        iconIV5 = view.findViewById(R.id.idIVIconDay5);
       // cityNameTV = view.findViewById(R.id.idTVCityName);
        weatherRVModelArrayList = new ArrayList<WeatherForecast>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());

        final RecyclerView rv = binding.idRvWeather;
        rv.setAdapter(new WeatherRecyclerViewAdapter(getContext(), weatherRVModelArrayList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //((WeatherRecyclerViewAdapter)rv.getAdapter()).setWeatherViewModel(mWeatherViewModel);

        mWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                // getting values from API weather service
                JSONObject curr = response.getJSONObject("current");
                String dt = curr.getString("dt");
                double temperature = Double.parseDouble(curr.getString("temp"));
                String condition  = curr.getJSONArray("weather").getJSONObject(0).getString("main");
                String conditionIcon   = curr.getJSONArray("weather").getJSONObject(0).getString("icon");
                // converting temp
                int temp = (int) Math.rint(temperature);
                //setting the information
                binding.idTVCityName.setText(cityName);
                binding.idTVCondition.setText(condition);
                binding.idTemp.setText(temp + "°F");
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(conditionIcon).concat(".png")).into(iconIV);

                binding.idTVDay1.setText("Tuesday");

            } catch(Exception e){ }
        });

        // TODO: add button that get weather data
        mWeatherViewModel.connect(String.valueOf(latitude), String.valueOf(longitude), mUserModel.getmJwt());

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


}