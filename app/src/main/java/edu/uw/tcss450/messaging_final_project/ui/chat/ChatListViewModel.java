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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.io.RequestQueueSingleton;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ChatListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Chatroom>> mChatroomList;
    private final MutableLiveData<JSONObject> mResponse;
    private UserInfoViewModel userInfoViewModel;

    /*
    * This determines which chat room we will navigate to
    */
    private int mChatId;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mChatroomList = new MutableLiveData<>();
        mChatroomList.setValue(new ArrayList<>());

        //userInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);

    }

    public void setChatId(int id){
        mChatId = id;
    }

    public int getmChatId(){
        return mChatId;
    }

    public ArrayList<Chatroom> getChats() {
        return mChatroomList.getValue();
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super ArrayList<Chatroom>> observer) {
        mChatroomList.observe(owner, observer);
    }

    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }


    public void setUserInfoViewModel(UserInfoViewModel info) {
        userInfoViewModel = info;
    }

    private void handleResult(final JSONObject result) {
        mResponse.setValue(result);

        try {
            JSONArray jsonArray = result.getJSONArray("rows");
            ArrayList<Chatroom> chatList = mChatroomList.getValue();
            chatList.clear();

            for(int i = 0;i < jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Chatroom chat = new Chatroom(json.getInt("chatid"),
                        json.getString("name"));

                chatList.add(chat);
                //Log.e("chatidds", chat.getChatId());
                Log.e("Chats",chat.getChatName());
            }

            mChatroomList.setValue(chatList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //mChatroomList.setValue(mChatroomList.getValue());
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());

    }

    /**
     * get users chats
     *
     * @param jwt
     */
    public void getChats(final String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url)+"chatinfo";
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
     * delete a chat based on the chats id and the user
     *
     * @param jwt
     * @param chatId chat id of the chat to be removed
     * @param email email of user to be removed from chat
     */
    public void deleteChat(final String jwt, final int chatId, String email) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/"
                + chatId + "/"
                + email;

        Request request = new JsonObjectRequest(Request.Method.DELETE,
                url,
                null,
                this::handleDeleteResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    private void handleDeleteResult(final JSONObject result) {
        try {
            Log.d("ChatListViewModel DELETE", "Result for delete attempt: " + result.getString("success"));
        } catch (JSONException e) {
            throw new IllegalStateException("Unexpected response in ChatListViewModel: " + result);
        }
    }

    /**
     * create a chat with a name and a user
     *
     * @param jwt
     * @param name name of new chat
     * @param email email of current user
     */
    public void addChat(final String jwt, final String name, final String email) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats";

        JSONObject body = new JSONObject();
        try {
            body.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                response -> handleAddChat(jwt, response, email),
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
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    /**
     * add the user to a chat they made
     *
     * @param jwt
     * @param response
     * @param email current users email
     */
    private void handleAddChat(final String jwt, final JSONObject response, final String email) {
        try {
            int chatID = response.getInt("chatID");
            //String email = response.getString("email");
            System.out.println(chatID);
            addMember(jwt, chatID, email);
            getChats(jwt);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * add a user to a chat
     *
     * @param jwt
     * @param chatID chatid number
     * @param email email of user to be added to chat
     */
    public void addMember(final String jwt, int chatID, String email) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/" + chatID + "/" + email;

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                mResponse::setValue,
                this::handleError) {

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
                RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }
}