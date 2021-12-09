package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import edu.uw.tcss450.messaging_final_project.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatCardBinding;
import edu.uw.tcss450.messaging_final_project.model.UserInfoViewModel;
import edu.uw.tcss450.messaging_final_project.ui.contacts.ContactEntry;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatViewHolder>{

    private Context context;
    private ArrayList<Chatroom> mChats;
    //private final Map<Chatroom, Boolean> mExpandedFlags;
    //private final ChatListFragment chatListFragment;
    private ChatListViewModel mChatListViewModel;
    //private Chatroom chatroom;
    private UserInfoViewModel mUserInfoViewModel;


    //come back to this
    public ChatListRecyclerViewAdapter(Context context, ArrayList<Chatroom> mChats, UserInfoViewModel userInfoViewModel) {
        this.context = context;
        this.mChats = mChats;
        mUserInfoViewModel = userInfoViewModel;
    }

    @NonNull
    @Override
    public ChatListRecyclerViewAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListRecyclerViewAdapter.ChatViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChatroomName(mChats.get(position).getChatName());
        holder.setChatRoom(mChats.get(position));
    }

    public void filterList(ArrayList<Chatroom> filterlist) {
        // on below line we are passing filtered
        // array list in our original array list
        mChats = filterlist;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mChats.size();
    }


    public void setChatListViewModel(ChatListViewModel chatListViewModel){
        mChatListViewModel = chatListViewModel;
    }
    public void setUserInfoViewModel(UserInfoViewModel userInfoViewModel){
        mUserInfoViewModel = userInfoViewModel;
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{
        private ImageView chatIV;
        public FragmentChatCardBinding binding;
        private Chatroom mChat;
        private TextView chatTV;
        private View mView;
        //UserInfoViewModel mUserInfoViewModel;



        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            //chatIV = itemView.findViewById(R.id.idIVChat);
            chatTV = itemView.findViewById(R.id.idTVChatName);
            binding = FragmentChatCardBinding.bind(itemView);

        }

        public void setChatroom(Chatroom chatroom){

        }

        public void setMenuButton(){
            binding.dots.setOnClickListener((button)->{
                showPopup();
            });
        }

        public void showPopup(){
            PopupMenu popupMenu = new PopupMenu(mView.getContext(), mView);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu_chat);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.delete:{
                    mChatListViewModel.setChatId(mChat.getChatId());
                    mChatListViewModel.deleteChat(mUserInfoViewModel.getmJwt(), mChat.getChatId(), mUserInfoViewModel.getEmail());
                    Log.e("ChatListRVHolder", mChat.getChatName() + ":" + mChat.getChatId());
                    ArrayList<Chatroom> list = mChats;//mChatListViewModel.getChatroomList();
                    for(int i = 0 ;i < list.size();i++){
                        if(mChat.getChatId()==list.get(i).getChatId()){
                            list.remove(i);
                        }
                    }
                    break;
                }
                case R.id.edit:{
                    mChatListViewModel.setChatId(mChat.getChatId());
                    System.out.println(mChatListViewModel.getmChatId());
                    Navigation.findNavController(mView).navigate(ChatListFragmentDirections.actionNavigationChatListToAddChatContactFragment());
                }
            }

            return false;
        }


        void setChatroomName(final String chatName) {
            binding.idTVChatName.setText(chatName);
        }

        void setChatRoom(final Chatroom chatroom) {
            mChat = chatroom;
            chatTV.setText(mChat.getChatName());

            mView.setOnClickListener((button)->{
                mChatListViewModel.setChatId(chatroom.getChatId());
                System.out.println(mChatListViewModel.getmChatId());
                Navigation.findNavController(mView)
                        .navigate(ChatListFragmentDirections
                                .actionNavigationChatListToNavigationChat());
            });
            setMenuButton();


//            binding.buttonOpen.setOnClickListener(view -> {
//                mChatListViewModel.setChatId(chatroom.getChatId());
//                System.out.println(mChatListViewModel.getmChatId());
//                Navigation.findNavController(mView)
//                        .navigate(ChatListFragmentDirections
//                                .actionNavigationChatListToNavigationChat());
//            });
//            binding.buttonLeave.setOnClickListener(view -> {
//                mChatListViewModel.setChatId(chatroom.getChatId());
//                mChatListViewModel.deleteChat(mUserInfoViewModel.getmJwt(), chatroom.getChatId(), mUserInfoViewModel.getEmail());
//                Log.e("ChatListRV",mUserInfoViewModel.getEmail());
//            });
//            binding.buttonEdit.setOnClickListener(view -> {
//                mChatListViewModel.setChatId(chatroom.getChatId());
//                System.out.println(mChatListViewModel.getmChatId());
//                Navigation.findNavController(view).navigate(ChatListFragmentDirections.actionNavigationChatListToAddChatContactFragment());
//            });
        }

        public Chatroom getChatroom() {
            return mChat;
        }


    }

    //public void setChatroomName(List<Chatroom> rooms) {
       // mChats = rooms;
    //}

}
