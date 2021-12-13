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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatListBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ChatListFragment extends Fragment {

    ChatListViewModel mChatListViewModel;
    UserInfoViewModel mUserInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chat list fragment", "onCreate");

        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mChatListViewModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mChatListViewModel.getChats(mUserInfoViewModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("chat list fragment", "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        binding.swipeContainer.setOnRefreshListener(() -> {
            //mContactsViewModel.getNextMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
            Log.e("ChatFrag","w");
            mChatListViewModel.getChats(mUserInfoViewModel.getmJwt());
        });

        binding.swipeContainer.setRefreshing(true);



        mChatListViewModel.addResponseObserver(getViewLifecycleOwner(), response ->{
            Log.e("ChatFrag","response refresh");
            binding.swipeContainer.setRefreshing(false);
        });

        final RecyclerView rv = binding.listRoot;
        rv.setAdapter(new ChatListRecyclerViewAdapter(getContext(), mChatListViewModel.getChats(), mUserInfoViewModel));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((ChatListRecyclerViewAdapter)rv.getAdapter()).setChatListViewModel(mChatListViewModel);

        FloatingActionButton fab = binding.createChatButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(ChatListFragmentDirections.actionNavigationChatListToAddChatFragment());

            }
        });
        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(),
                list -> {

                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                    //binding.layoutWait.setVisibility(View.GONE);
                });


    }


}