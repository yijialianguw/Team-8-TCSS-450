package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactCardBinding;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    // creating variables for context and array list.
    private Context context;
    private ArrayList<ContactEntry> contactEntryArrayList;

    // creating a constructor
    public ContactsRecyclerViewAdapter(Context context, ArrayList<ContactEntry> contactEntryArrayList) {
        this.context = context;
        this.contactEntryArrayList = contactEntryArrayList;
    }

    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ContactsRecyclerViewAdapter.ViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.fragment_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setContactName(contactEntryArrayList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return contactEntryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line creating a variable
        // for our image view and text view.
        private ImageView contactIV;
        private TextView contactTV;
        private FragmentContactCardBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image view and text view.
            contactIV = itemView.findViewById(R.id.idIVContact);
            contactTV = itemView.findViewById(R.id.idTVContactName);
            binding = FragmentContactCardBinding.bind(itemView);

        }

        void setContactName(final String contactName){
            binding.idTVContactName.setText(contactName);
        }


    }
}