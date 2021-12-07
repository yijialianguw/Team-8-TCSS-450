package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // TODO: have button on contact to delete



    // TODO: when user click on contact, it take you to a chat with them
    // TODO: this means that whenever a new contact is created, we must create a kind of 'DM' chat for them
    // TODO: give this chat a generic name like "UserA and UserB chat"
    // TODO: add column to Contacts table: chatId
    // TODO: this will be the chatID of the auto generated chat room

    // TODO: OR
    // TODO: contact card will have a button for creating a new chat with that person



    ContactsViewModel mContactsViewModel;
    UserInfoViewModel mUserInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ContactsFragment", "onCreate");
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfoViewModel = provider.get(UserInfoViewModel.class);
        mContactsViewModel = provider.get(ContactsViewModel.class);
        mContactsViewModel.getContacts(mUserInfoViewModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.e("ContactsFragment", "onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Log.e("ContactsFrag", "onViewCreated");
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true); // TODO: why doesnt this actaully work


        final RecyclerView rv = binding.recyclerContacts;
        rv.setAdapter(new ContactsRecyclerViewAdapter(mContactsViewModel.getContactsList()));

        ((ContactsRecyclerViewAdapter)rv.getAdapter()).setUserInfoViewModel(mUserInfoViewModel);
        ((ContactsRecyclerViewAdapter)rv.getAdapter()).setContactsViewModel(mContactsViewModel);

        //rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        binding.swipeContainer.setOnRefreshListener(() -> {
            //mContactsViewModel.getNextMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
            //Log.e("ContactFrag","adfafa");
            mContactsViewModel.getContacts(mUserInfoViewModel.getmJwt());
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));



//        mContactsViewModel.addResponseObserver(getViewLifecycleOwner(), response ->{
//            Log.e("ContactFrag","response refresh");
//            binding.swipeContainer.setRefreshing(false);
//        });

        mContactsViewModel.addContactListObserver(getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    //Log.e("Contact Frag", "list observed" + list.size());
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(0);
                    binding.swipeContainer.setRefreshing(false);
                    //binding.layoutWait.setVisibility(View.GONE);
                });


        binding.buttonAddContact.setOnClickListener(button ->{
            mContactsViewModel.sendInvite(binding.contactTextInput.getText().toString(), mUserInfoViewModel.getmJwt());
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