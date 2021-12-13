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

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentAddChatContactBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactsViewModel;

public class AddChatContactFragment extends Fragment {


    private FragmentAddChatContactBinding binding;

    private ArrayList<String> userNames;

    ChatListViewModel mChatListViewModel;
    UserInfoViewModel mUserInfoViewModel;
    ContactsViewModel mContactsViewModel;
    ChatListRecyclerViewAdapter mChatListRV;
    ChatListRecyclerViewAdapter.ChatViewHolder mChatViewHolder;

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
        return inflater.inflate(R.layout.fragment_add_chat_contact, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        final RecyclerView rv = binding.recyclerContacts;
//        rv.setAdapter(new ContactsRecyclerViewAdapter(mContactsViewModel.getContactsList()));
//        ((ContactsRecyclerViewAdapter)rv.getAdapter()).setUserInfoViewModel(mUserInfoViewModel);


        binding = FragmentAddChatContactBinding.bind(getView());

        binding.buttonChatContact.setOnClickListener(button -> {
            addContactToChat();
            Navigation.findNavController(button).navigate(AddChatContactFragmentDirections.actionAddChatContactFragmentToNavigationChatList());

        });

    }

    private void addContactToChat() {
        int chatID = mChatListViewModel.getmChatId();
        String contact = binding.enterContact.getText().toString().trim();
        if(contact.contains("@")){
            mChatListViewModel.addMember(mUserInfoViewModel.getmJwt(), chatID, contact);
        }else{
            binding.enterContact.setError("Please enter a valid contact email");
        }
    }

}