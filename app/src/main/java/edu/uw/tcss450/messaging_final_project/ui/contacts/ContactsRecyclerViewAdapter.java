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

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

        // creating variables for context and array list.
        private Context context;
        private ArrayList<ContactEntry> contactEntryArrayList;

        // creating a constructor
        public ContactsRecyclerViewAdapter(Context context, ArrayList<ContactEntry> contactsModalArrayList) {
            this.context = context;
            this.contactEntryArrayList = contactsModalArrayList;
        }

        @NonNull
        @Override
        public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // passing our layout file for displaying our card item
            return new ContactsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_contact_card, parent, false));

        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    // below method is use for filtering data in our array list
        public void filterList(ArrayList<ContactEntry> filterllist) {
            // on below line we are passing filtered
            // array list in our original array list
            contactEntryArrayList = filterllist;
            notifyDataSetChanged();
        }

      /**  @Override
        public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
            // getting data from array list in our modal.
            ContactEntry entry = contactEntryArrayList.get(position);
            // on below line we are setting data to our text view.
            holder.contactTV.setText(entry.getUserName());
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getRandomColor();

            // below text drawable is a circular.
            TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                    .width(100)  // width in px
                    .height(100) // height in px
                    .endConfig()
                    // as we are building a circular drawable
                    // we are calling a build round method.
                    // in that method we are passing our text and color.
                    .buildRound(entry.getUserName().substring(0, 1), color);
            // setting image to our image view on below line.
            holder.contactIV.setImageDrawable(drawable2);
        }
       */

        @Override
        public int getItemCount() {
            return contactEntryArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // on below line creating a variable
            // for our image view and text view.
            private ImageView contactIV;
            private TextView contactTV;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our image view and text view.
                contactIV = itemView.findViewById(R.id.idIVContact);
                contactTV = itemView.findViewById(R.id.idTVContactName);
            }
        }
    }
