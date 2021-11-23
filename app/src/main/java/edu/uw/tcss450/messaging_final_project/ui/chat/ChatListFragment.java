package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatListBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ChatListFragment extends Fragment {

    private ChatListViewModel mModel;
    private FragmentChatListBinding binding;
    private ChatListRecyclerViewAdapter chatListRVA;
    private UserInfoViewModel mUserInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mModel.connectGet(mUserInfoViewModel.getmJwt());
        mModel.setUserInfoViewModel(mUserInfoViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentChatListBinding.bind(getView());
        chatListRVA = new ChatListRecyclerViewAdapter(new ArrayList<>(), this);
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (!chatList.isEmpty()) {
                chatListRVA.setChatroom(chatList);
            }
        });
    }

}

