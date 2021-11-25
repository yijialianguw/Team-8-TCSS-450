package edu.uw.tcss450.messaging_final_project.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        FragmentAccountBinding binding = FragmentAccountBinding.bind(getView());

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