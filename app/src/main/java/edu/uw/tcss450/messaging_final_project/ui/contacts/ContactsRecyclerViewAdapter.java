package edu.uw.tcss450.messaging_final_project.ui.contacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.MyViewHolder> {

    // creating variables for context and array list.
    //private Context context;
    private ArrayList<ContactEntry> contactEntryArrayList;
    private ContactsViewModel mContactsViewModel;
    private UserInfoViewModel mUserInfoViewModel;

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
        holder.setContact(contact);
        holder.setContactName();
        holder.setMenuButton();

        holder.setYesButton(contact.getMemberId());


        holder.setInviteVisibility();

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

    public void setContactsViewModel(ContactsViewModel viewModel){
        mContactsViewModel = viewModel;
    }

    public void setUserInfoViewModel(UserInfoViewModel userInfoViewModel){
        mUserInfoViewModel = userInfoViewModel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{
        // on below line creating a variable
        // for our image view and text view.
        private FragmentContactCardBinding binding;
        private View mItemView;

        private ContactEntry mContactEntry;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mItemView = itemView;

            binding = FragmentContactCardBinding.bind(itemView);

            binding.buttonNo.setVisibility(View.GONE);
            binding.buttonYes.setVisibility(View.GONE);

        }

        public void setContact(ContactEntry contactEntry){
            mContactEntry = contactEntry;
        }

        public void setYesButton(final String memberid){
            binding.buttonYes.setOnClickListener((button)->{
                mContactsViewModel.acceptInvite(memberid,mUserInfoViewModel.getmJwt());
                mContactEntry.setInvite(false);
            });
        }

        public void setMenuButton(){
            binding.dots.setOnClickListener((button)->{
                showPopup();
            });
        }

        public void setNoButton(final String memberid){
            // TODO: delete contact
        }

        public void setInviteVisibility(){
            if(mContactEntry.isInvite()){
                binding.buttonNo.setVisibility(View.VISIBLE);
                binding.buttonYes.setVisibility(View.VISIBLE);
                binding.idTVContactName.setText("NEW INVITE:\n"+binding.idTVContactName.getText());
            } else {
                binding.buttonNo.setVisibility(View.GONE);
                binding.buttonYes.setVisibility(View.GONE);
            }

        }

        void setContactName() {
            binding.idTVContactName.setText(mContactEntry.getUserName());
        }

        public void showPopup(){
            PopupMenu popupMenu = new PopupMenu(mItemView.getContext(), mItemView);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Log.e("ContactsRVHolder", mContactEntry.getFirstName() + ": " + mContactEntry.getMemberId());
            mContactsViewModel.deleteContact(mContactEntry.getMemberId(), mUserInfoViewModel.getmJwt());
            ArrayList<ContactEntry> list = mContactsViewModel.getContactsList();
            for(int i = 0;i < list.size() ;i++){
                if(mContactEntry.getMemberId().equals(list.get(i).getMemberId())){
                    list.remove(i);
                }
            }
            return false;
        }
    }
}