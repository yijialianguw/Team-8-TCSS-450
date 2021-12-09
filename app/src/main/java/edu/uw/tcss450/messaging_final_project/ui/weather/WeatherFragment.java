package edu.uw.tcss450.messaging_final_project.ui.weather;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.messaging_final_project.model.LocationViewModel;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Contains the weather information.
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel mWeatherViewModel;
    private UserInfoViewModel mUserModel;
    private LocationViewModel mLocationModel;

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
        latitude = 41.89705550448851;
        longitude = -87.65438071838535;
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mWeatherViewModel = provider.get(WeatherViewModel.class);
        cityName = getCityName(longitude, latitude);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        iconIV = view.findViewById(R.id.idIVIcon);
        iconIV1 = view.findViewById(R.id.idIVIconDay1);
        iconIV2 = view.findViewById(R.id.idIVIconDay2);
        iconIV3 = view.findViewById(R.id.idIVIconDay3);
        iconIV4 = view.findViewById(R.id.idIVIconDay4);
        iconIV5 = view.findViewById(R.id.idIVIconDay5);
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

        mLocationModel = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        mLocationModel.addLocationObserver(getViewLifecycleOwner(), location -> {
                    longitude = location.getLongitude();
                    latitude = location.getLongitude();
                });

        mWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                // getting values from API weather service
                JSONObject curr = response.getJSONObject("current");
                double temperature = Double.parseDouble(curr.getString("temp"));
                String condition  = curr.getJSONArray("weather").getJSONObject(0).getString("main");
                String conditionIcon   = curr.getJSONArray("weather").getJSONObject(0).getString("icon");
                String day_c = getDayName(curr.getString("dt"));
                // converting temp
                int temp = (int) Math.rint(temperature);
                //setting the information
                binding.idTVCityName.setText(cityName);
                binding.idTVCondition.setText(condition);
                binding.idTemp.setText(temp + "°F");
                binding.idDayC.setText(day_c);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(conditionIcon).concat(".png")).into(iconIV);

                // THIS IS JUST FOR TESTING PURPOSES
                binding.idTVDay1.setText("Tuesday");

                // ATTEMPT FOR DAILY
                JSONArray daily = response.getJSONArray("daily");

                //DAY 1
                String tempH1 = daily.getJSONObject(0).getJSONObject("temp").getString("max");
                String tempL1 = daily.getJSONObject(0).getJSONObject("temp").getString("min");
                String iconD1   = daily.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                String day1 = getDayName(daily.getJSONObject(0).getString("dt"));
                int tempH_1 = Math.round(Float.parseFloat(tempH1));
                int tempL_1 = Math.round(Float.parseFloat(tempL1));
                binding.idTVMax1.setText(String.valueOf(tempH_1));
                binding.idTVMin1.setText(String.valueOf(tempL_1));
                binding.idTVDay1.setText(day1);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(iconD1).concat(".png")).into(iconIV1);

                //DAY 2
                String tempH2 = daily.getJSONObject(1).getJSONObject("temp").getString("max");
                String tempL2 = daily.getJSONObject(1).getJSONObject("temp").getString("min");
                String iconD2   = daily.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon");
                String day2 = getDayName(daily.getJSONObject(1).getString("dt"));
                int tempH_2 = Math.round(Float.parseFloat(tempH2));
                int tempL_2 = Math.round(Float.parseFloat(tempL2));
                binding.idTVMax2.setText(String.valueOf(tempH_2));
                binding.idTVMin2.setText(String.valueOf(tempL_2));
                binding.idTVDay2.setText(day2);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(iconD2).concat(".png")).into(iconIV2);

                //DAY 3
                String tempH3 = daily.getJSONObject(2).getJSONObject("temp").getString("max");
                String tempL3 = daily.getJSONObject(2).getJSONObject("temp").getString("min");
                String iconD3   = daily.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("icon");
                String day3 = getDayName(daily.getJSONObject(2).getString("dt"));
                int tempH_3 = Math.round(Float.parseFloat(tempH3));
                int tempL_3 = Math.round(Float.parseFloat(tempL3));
                binding.idTVMax3.setText(String.valueOf(tempH_3));
                binding.idTVMin3.setText(String.valueOf(tempL_3));
                binding.idTVDay3.setText(day3);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(iconD3).concat(".png")).into(iconIV3);

                //DAY 4
                String tempH4 = daily.getJSONObject(3).getJSONObject("temp").getString("max");
                String tempL4 = daily.getJSONObject(3).getJSONObject("temp").getString("min");
                String iconD4   = daily.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("icon");
                String day4 = getDayName(daily.getJSONObject(3).getString("dt"));
                int tempH_4 = Math.round(Float.parseFloat(tempH4));
                int tempL_4 = Math.round(Float.parseFloat(tempL4));
                binding.idTVMax4.setText(String.valueOf(tempH_4));
                binding.idTVMin4.setText(String.valueOf(tempL_4));
                binding.idTVDay4.setText(day4);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(iconD4).concat(".png")).into(iconIV4);

                //DAY 5
                String tempH5 = daily.getJSONObject(4).getJSONObject("temp").getString("max");
                String tempL5 = daily.getJSONObject(4).getJSONObject("temp").getString("min");
                String iconD5   = daily.getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon");
                String day5 = getDayName(daily.getJSONObject(4).getString("dt"));
                int tempH_5 = Math.round(Float.parseFloat(tempH5));
                int tempL_5 = Math.round(Float.parseFloat(tempL5));
                binding.idTVDay5.setText(day5);
                Picasso.get().load("https://openweathermap.org/img/wn/".concat(iconD5).concat(".png")).into(iconIV5);
                binding.idTVMax5.setText(tempH5.substring(0,2));
                binding.idTVMin5.setText(tempL5.substring(0,2));
            } catch(Exception e){
                Log.e("Prsing Exception", e.getMessage());
            }
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

    private String getDayName (String epoch){
        Date date1 = new Date (Integer.valueOf(epoch)*1000);
        Calendar c = Calendar.getInstance();
        c.setTime(date1);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // this will for example return 3 for tuesday
        String dayString;
        switch (dayOfWeek) {
            case 1: dayString = "Sunday";
                    break;
            case 2: dayString = "Monday";
                break;
            case 3: dayString = "Tuesday";
                break;
            case 4: dayString = "Wednesday";
                break;
            case 5: dayString = "Thursday";
                break;
            case 6: dayString = "Friday";
                break;
            case 7: dayString = "Saturday";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dayOfWeek);
        }
        return dayString;
    }

}