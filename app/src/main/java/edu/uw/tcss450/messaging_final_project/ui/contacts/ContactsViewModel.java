package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

import edu.uw.tcss450.messaging_final_project.R;


public class ContactsViewModel extends AndroidViewModel {

    //private MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<ArrayList<ContactEntry>> mContactList;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
//        mResponse = new MutableLiveData<>();
//        mResponse.setValue(new JSONObject());
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
    }
    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super ArrayList<ContactEntry>> observer) {
        mContactList.observe(owner, observer);
    }

//    public void addResponseObserver(@NonNull LifecycleOwner owner,
//                                    @NonNull Observer<? super JSONObject> observer){
//        mResponse.observe(owner,observer);
//    }

    private void handleError(final VolleyError error) { // TODO: better handling
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

    private void handleResult(final JSONObject result) {
//        IntFunction<String> getString =
//                getApplication().getResources()::getString;

        // TODO: add contacts to contact list

        try {
            JSONArray jsonArray = result.getJSONArray("rows");
            ArrayList<ContactEntry> contactList = new ArrayList<>();

            for(int i = 0;i < jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                ContactEntry entry = new ContactEntry(json.getString("memberid"),
                        json.getString("username"),
                        json.getString("firstname"),
                        json.getString("lastname"));
                contactList.add(entry);
                Log.e("Contacts",entry.getUserName());
            }

            mContactList.setValue(contactList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        try {
            JSONObject root = result;
            if(true){

            if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
                JSONObject response =
                        root.getJSONObject(getString.apply(
                                R.string.keys_json_blogs_response));

                if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
                    JSONArray data = response.getJSONArray(
                            getString.apply(R.string.keys_json_blogs_data));
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonBlog = data.getJSONObject(i);
                        ContactEntry entry = new ContactEntry.Builder(
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_blogs_pubdate)),
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_blogs_title)))
                                .addTeaser(jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_blogs_teaser)))
                                .addUrl(jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_blogs_url)))
                                .build();
                        if (!mContactList.getValue().contains(entry)) {
                            mContactList.getValue().add(entry);
                        }
                    }
                } else {
                    Log.e("ERROR!", "No data array");
                }
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }

 */
        //mContactList.setValue(mContactList.getValue());
    }

    public void connectGet(final String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url)+"contacts";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
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
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
}
