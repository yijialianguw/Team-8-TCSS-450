package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("ContactsFragment", "asdfafafasfasdfasfas");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ContactsFragment", "asdfafafasfasdfasfas");

        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        mContactsViewModel.connectGet(mUserInfoViewModel.getmJwt());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());
        mContactsViewModel.addContactListObserver(getViewLifecycleOwner(), blogList -> {
            if (!blogList.isEmpty()) {
//                binding.listRoot.setAdapter(
//                        new ContactsRecyclerViewAdapter(getContext(),blogList));
//                binding.layoutWait.setVisibility(View.GONE);
            }
        });
    }

}