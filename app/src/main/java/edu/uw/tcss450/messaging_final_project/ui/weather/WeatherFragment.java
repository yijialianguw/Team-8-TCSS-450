package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatBinding;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;
import edu.uw.tcss450.messaging_final_project.ui.chat.ChatSendViewModel;
import edu.uw.tcss450.messaging_final_project.ui.chat.ChatViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Contains the weather information.
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel mWeatherViewModel;
    private UserInfoViewModel mUserModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        //Log.e("Weather", mUserModel.getmJwt());
        mWeatherViewModel = provider.get(WeatherViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());

        mWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                JSONObject curr = response.getJSONObject("current");
                String str = "Temperature: " + curr.getString("temp") + " F\nSunrise: " + curr.getString("sunrise") + "\nSunset: " + curr.getString("sunset")+ "\nWeather: " + curr.getJSONArray("weather").getJSONObject(0).getString("main");
                binding.editWeather.setText(str);
            } catch(Exception e){

            }
        });

        // TODO: add button that get weather data
        String LAT = "10";
        String LON = "10";
        mWeatherViewModel.connect(LAT, LON, mUserModel.getmJwt());

    }


}