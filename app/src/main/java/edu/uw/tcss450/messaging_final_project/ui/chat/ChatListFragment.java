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

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatListBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ChatListFragment extends Fragment {

    ChatListViewModel mChatListViewModel;
    UserInfoViewModel mUserInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ContactsFragment", "onCreate");

        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mChatListViewModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mChatListViewModel.getChats(mUserInfoViewModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("ContactsFragment", "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());
        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
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
        rv.setAdapter(new ChatListRecyclerViewAdapter(getContext(), mChatListViewModel.getChats()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                    //binding.layoutWait.setVisibility(View.GONE);
                });


//        mContactsViewModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
//            if (!contactList.isEmpty()) {
//                binding.listRoot.setAdapter(
//                        new ContactsRecyclerViewAdapter(getContext(),blogList));
//                binding.layoutWait.setVisibility(View.GONE);
//            }
//        });

    }


//    public List<ContactEntry> getMessageListByChatId(final int chatId) {
//        return getOrCreateMapEntry(chatId).getValue();
//    }
//
//    private MutableLiveData<List<ContactEntry>> getOrCreateMapEntry(final int chatId) {
//        if(!mMessages.containsKey(chatId)) {
//            mMessages.put(chatId, new MutableLiveData<>(new ArrayList<>()));
//        }
//        return mMessages.get(chatId);
//    }



}