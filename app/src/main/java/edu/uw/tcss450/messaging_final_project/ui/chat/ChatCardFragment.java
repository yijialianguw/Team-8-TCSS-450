package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uw.tcss450.messaging_final_project.R;
import androidx.fragment.app.Fragment;

public class ChatCardFragment extends Fragment {
    public ChatCardFragment() {
        //empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_card, container, false);
    }
}