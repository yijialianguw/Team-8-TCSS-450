package edu.uw.tcss450.messaging_final_project.ui.weather;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.messaging_final_project.io.RequestQueueSingleton;

/**
 * The View Model for Weather data.
 */
public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<ArrayList<WeatherForecast>> mWeatherForecasts;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        ArrayList<WeatherForecast> tmp = new ArrayList<>();
        mWeatherForecasts = new MutableLiveData<>();
        mWeatherForecasts.setValue(tmp);
    }

    public ArrayList<WeatherForecast> getWeatherForecasts(){
        return mWeatherForecasts.getValue();
    }

    public void addWeatherObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<ArrayList<WeatherForecast>> observer) {
        mWeatherForecasts.observe(owner, observer);
    }


    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    public void connect(final String lat, final String lon, final String jwt) {
        // TODO: like all of this
        String url = "https://team8-tcss450-server.herokuapp.com/weather" + "?lat=" + lat + "&" + "lon=" + lon;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);

    }

    private void handleSuccess(final JSONObject response) {
        mResponse.setValue(response);

        populateForecasts(response);


    }

    private void populateForecasts(final JSONObject response){
        try {
            JSONArray hourly= response.getJSONArray("hourly");
            mWeatherForecasts.getValue().clear();
            for(int i = 0 ;i < hourly.length();i++){
                JSONObject hour = hourly.getJSONObject(i);
                String temp = hour.getString("temp");
                String time = hour.getString("dt");
                String icon = hour.getJSONArray("weather").getJSONObject(0).getString("icon");
                String wind_speed = hour.getString("wind_speed");
//                Log.e("WeatherVM",69+"");
//                Log.e("WeatherVM",temp);
//                Log.e("WeatherVM",icon);
//                Log.e("WeatherVM",wind_speed);

                mWeatherForecasts.getValue().add(new WeatherForecast(69,temp,icon,wind_speed));
                // TODO: 69 is just a placeholder until we can get the actaul hour
            }

            mWeatherForecasts.setValue(mWeatherForecasts.getValue());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}