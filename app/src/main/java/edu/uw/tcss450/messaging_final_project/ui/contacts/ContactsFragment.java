package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactsBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    ContactsViewModel mContactsViewModel;
    UserInfoViewModel mUserInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ContactsFragment", "onCreate");

        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        mContactsViewModel.getContacts(mUserInfoViewModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("ContactsFragment", "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());
        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            //mContactsViewModel.getNextMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
            Log.e("ContactFrag","adfafa");
            mContactsViewModel.getContacts(mUserInfoViewModel.getmJwt());
        });

        binding.swipeContainer.setRefreshing(true);


        mContactsViewModel.addResponseObserver(getViewLifecycleOwner(), response ->{
            Log.e("ContactFrag","response refresh");
            binding.swipeContainer.setRefreshing(false);
        });

        final RecyclerView rv = binding.recyclerContacts;
        rv.setAdapter(new ContactsRecyclerViewAdapter(getContext(), mContactsViewModel.getContacts()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mContactsViewModel.addContactListObserver(getViewLifecycleOwner(),
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