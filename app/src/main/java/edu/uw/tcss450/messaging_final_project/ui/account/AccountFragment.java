package edu.uw.tcss450.messaging_final_project.ui.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.messaging_final_project.MainActivity;
import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentAccountBinding;
import edu.uw.tcss450.messaging_final_project.model.PushyTokenViewModel;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;
import edu.uw.tcss450.messaging_final_project.ui.chat.ChatListViewModel;
import edu.uw.tcss450.messaging_final_project.ui.chat.ChatSendViewModel;
import edu.uw.tcss450.messaging_final_project.ui.chat.ChatViewModel;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactEntry;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private AccountViewModel mAccountViewModel;
    private UserInfoViewModel mUserInfoViewModel;
    private ContactEntry mContactsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mAccountViewModel = provider.get(AccountViewModel.class);
        mUserInfoViewModel = provider.get(UserInfoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        FragmentAccountBinding binding = FragmentAccountBinding.bind(getView());
        binding.userEmail.setText(mUserInfoViewModel.getmEmail());
        //binding.userName.setText(mUserInfoViewModel.getmName()); // TODO: does the userinfoviewmodel not have username? or is email == username?
        //binding.userUserName.setText(mUserInfoViewModel.getmUserName());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        binding.switchDarkMode.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();
                        }
                    }
                });


        binding.buttonSignOut.setOnClickListener(button ->{
            signOut();
        });


    }

    private void signOut() {
        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        PushyTokenViewModel model = new ViewModelProvider(getActivity())
                .get(PushyTokenViewModel.class);
        //when we hear back from the web service quit
        model.addResponseObserver(getActivity(), result -> getActivity().finishAndRemoveTask());
        model.deleteTokenFromWebservice(
                new ViewModelProvider(getActivity())
                        .get(UserInfoViewModel.class)
                        .getmJwt()
        );
    }



}