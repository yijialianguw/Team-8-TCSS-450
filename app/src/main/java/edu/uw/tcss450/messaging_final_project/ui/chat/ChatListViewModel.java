package edu.uw.tcss450.messaging_final_project.ui.chat;

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

import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ChatListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Chatroom>> mChatRoomList;
    private final MutableLiveData<JSONObject> mResponse;
    private UserInfoViewModel userInfoViewModel;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Chatroom>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    public void connectGet(String jwt) {
        String url = "https://team8-tcss450-server.herokuapp.com/chat";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleResult,
                this::handleError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
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

    public void setUserInfoViewModel(UserInfoViewModel info) {
        userInfoViewModel = info;
    }

    private void handleResult(final JSONObject result) {
        ArrayList<Chatroom> temp = new ArrayList<>();
        try {
            JSONArray chats = result.getJSONArray("chats");
            for (int i = 0; i < chats.length(); i++) {
                JSONObject chat = chats.getJSONObject(i);
                int chatid = chat.getInt("chat");
                String chatTitle = chat.getString("name");
                Chatroom post = new Chatroom(chatid, chatTitle);
                temp.add(post);
            }
        } catch (JSONException e) {
            Log.e("ERROR", "Found in handle Success ChatViewModel");
            Log.e("ERROR", "Error: " + e.getMessage());
        }
        mChatRoomList.setValue(temp);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());

    }

}