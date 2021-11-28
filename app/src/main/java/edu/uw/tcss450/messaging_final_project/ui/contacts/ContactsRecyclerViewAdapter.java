package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONObject;

import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactCardBinding;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.MyViewHolder> {

    // creating variables for context and array list.
    //private Context context;
    private ArrayList<ContactEntry> contactEntryArrayList;

    // creating a constructor
    public ContactsRecyclerViewAdapter(ArrayList<ContactEntry> contactEntryArrayList) {
        //this.context = context;
        this.contactEntryArrayList = contactEntryArrayList;
        //Log.e("Recycler", "Constructor");
    }

    public void setContactEntryArrayList(ArrayList<ContactEntry> contactEntryArrayList) {

        this.contactEntryArrayList = contactEntryArrayList;
    }

    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        //Log.e("Recycler", "onCreateViewHolder");
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactEntry contact = contactEntryArrayList.get(position);
        holder.setContactName(contact.getUserName());
        //Log.e("Recycler", "onBindViewHolder");
        // set click listener
        holder.setChatButtonListener(1);
    }

    // below method is use for filtering data in our array list
    public void filterList(ArrayList<ContactEntry> filterllist) {
        // on below line we are passing filtered
        // array list in our original array list
        contactEntryArrayList = filterllist;
        notifyDataSetChanged();
    }

    /**
     * @Override public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
     * // getting data from array list in our modal.
     * ContactEntry entry = contactEntryArrayList.get(position);
     * // on below line we are setting data to our text view.
     * holder.contactTV.setText(entry.getUserName());
     * ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
     * // generate random color
     * int color = generator.getRandomColor();
     * <p>
     * // below text drawable is a circular.
     * TextDrawable drawable2 = TextDrawable.builder().beginConfig()
     * .width(100)  // width in px
     * .height(100) // height in px
     * .endConfig()
     * // as we are building a circular drawable
     * // we are calling a build round method.
     * // in that method we are passing our text and color.
     * .buildRound(entry.getUserName().substring(0, 1), color);
     * // setting image to our image view on below line.
     * holder.contactIV.setImageDrawable(drawable2);
     * }
     */

    @Override
    public int getItemCount() {
        //Log.e("Recycler", "getItemCount" + contactEntryArrayList.size());

        return contactEntryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // on below line creating a variable
        // for our image view and text view.
        private ImageView contactIV;
        private TextView contactTV;
        private FragmentContactCardBinding binding;
        private View mItemView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mItemView = itemView;
            // initializing our image view and text view.
            contactIV = itemView.findViewById(R.id.idIVContact);
            contactTV = itemView.findViewById(R.id.idTVContactName);
            binding = FragmentContactCardBinding.bind(itemView);
            binding.buttonNo.setVisibility(View.GONE);
            binding.buttonYes.setVisibility(View.GONE);
            binding.buttonYes.setOnClickListener(button -> {
                //Log.e("ContactCardFragment", "afsaffasfsaf");
            });

            itemView.setOnClickListener(button -> {
                Log.e("ContactHolder", "contact cllicked");
            });

            // binding.{username-should-be-a-button}.setOnClickListener( button -> {
            //  // TODO: need to send a request to get chat room
            //  // TODO: should be able to have access to chatId of DM
            //

            // TODO: have a delete button for deleting contacts

        }

        void setContactName(final String contactName) {
            binding.idTVContactName.setText(contactName);
        }

        void setChatButtonListener(final int chatId){
            // set on click listener to button
            // use view model to send a request to web service
            // once response gets back, naviagte to chat
            // contactsfragment should have a navigateToChat method

            binding.buttonYes.setOnClickListener(button -> {
                Navigation.findNavController(mItemView)
                        .navigate(ContactsFragmentDirections
                                .actionNavigationContactsToNavigationChat());
            });
        }


//        void getChatRoom(JSONObject jsonObject){
//
//        }




    }
}
