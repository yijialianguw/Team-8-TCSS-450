package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.MainActivity;
import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentAddChatBinding;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactsBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactsRecyclerViewAdapter;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactsViewModel;

public class AddChatFragment extends Fragment {


    private FragmentAddChatBinding binding;

    private ArrayList<String> userNames;

    ChatListViewModel mChatListViewModel;
    UserInfoViewModel mUserInfoViewModel;
    ContactsViewModel mContactsViewModel;
    ChatListRecyclerViewAdapter mChatListRV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("add chat fragment", "onCreate");

        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mChatListViewModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mChatListViewModel.getChats(mUserInfoViewModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("add chat fragment", "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_chat, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddChatBinding.bind(getView());

        binding.buttonAddChat.setOnClickListener(button -> createChat());

    }

    private void createChat() {
        String name = binding.enterName.getText().toString().trim();
        if(name.length() < 2){
            binding.enterName.setError("Please enter a valid chat room name");
        }else{
            mChatListViewModel.addChat(mUserInfoViewModel.getmJwt(), name);
        }
    }

}
