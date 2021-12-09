package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;


public class ContactsViewModel extends AndroidViewModel {

    //private MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<ArrayList<ContactEntry>> mContactList;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());

    }

    public ArrayList<ContactEntry> getContactsList(){
        return mContactList.getValue();
    }
    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super ArrayList<ContactEntry>> observer) {
        mContactList.observe(owner, observer);
    }



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
        try {
            ArrayList<ContactEntry> contactList = mContactList.getValue();
            contactList.clear();
            JSONArray jsonArrayInvites = result.getJSONArray("invites");
            for(int i = 0 ;i < jsonArrayInvites.length();i++){
                JSONObject jsonInvite = jsonArrayInvites.getJSONObject(i);
                ContactEntry entry = new ContactEntry(jsonInvite.getString("memberid"),
                        jsonInvite.getString("username"),
                        jsonInvite.getString("firstname"),
                        jsonInvite.getString("lastname"));
                entry.setInvite(true);
                contactList.add(entry);
            }
            JSONArray jsonArray = result.getJSONArray("rows");
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                ContactEntry entry = new ContactEntry(json.getString("memberid"),
                        json.getString("username"),
                        json.getString("firstname"),
                        json.getString("lastname"));
                contactList.add(entry);
//                contactList.add(entry);
//                contactList.add(entry);
//                contactList.add(entry);
//                contactList.add(entry);
//                contactList.add(entry);
                Log.e("Contacts",entry.getUserName());
            }
            mContactList.setValue(contactList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //mContactList.setValue(mContactList.getValue());

    }

    public void getContacts(final String jwt) {
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

    /**
     * Accepts a contact invite.
     * @param memberid memberid of user that originally sent the invite
     * @param jwt
     */
    public void acceptInvite(final String memberid, final String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url)+"contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("memberid", memberid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body, //no body for this get request
                this::handleSomething,
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




    private void handleSomething(final JSONObject response){
        // idk

        mContactList.setValue(mContactList.getValue());
    }

    private void handleInviteContactError(final VolleyError error){
        // TODO: let user know maybe
    }


    /**
     * Sends and contact invite to another user.
     * @param email email of user that is being invited
     * @param jwt
     */
    public void sendInvite(final String email, final String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url)+"contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //no body for this get request
                this::handleSomething,
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


    /**
     * Deletes a contact.
     * @param memberid memberid of user that is being deleted
     * @param jwt
     */
    public void deleteContact(final String memberid, final String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url)+"contacts?memberid="+memberid;

//        JSONObject body = new JSONObject();
//        try {
//            body.put("memberid", memberid);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, // DELETE in volley dont have body
                this::handleSomething,
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