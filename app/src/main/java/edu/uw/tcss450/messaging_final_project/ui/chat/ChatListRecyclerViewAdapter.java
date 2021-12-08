package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatViewHolder>{

    private Context context;
    private ArrayList<Chatroom> mChats;
    //private final Map<Chatroom, Boolean> mExpandedFlags;
    //private final ChatListFragment chatListFragment;
    private ChatListViewModel mChatListViewModel;
    private Chatroom chatroom;
    private UserInfoViewModel mUserInfoViewModel;


    //come back to this
    public ChatListRecyclerViewAdapter(Context context, ArrayList<Chatroom> mChats) {
        this.context = context;
        this.mChats = mChats;
        //mExpandedFlags = mChats.stream().collect(Collectors.toMap(Function.identity(), blog->false));
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


    public class ChatViewHolder extends RecyclerView.ViewHolder {
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


        void setChatroomName(final String chatName) {
            binding.idTVChatName.setText(chatName);
        }

        void setChatRoom(final Chatroom chatroom) {
            mChat = chatroom;
            chatTV.setText(mChat.getChatName());


            binding.buttonOpen.setOnClickListener(view -> {
                mChatListViewModel.setChatId(chatroom.getChatId());
                System.out.println(mChatListViewModel.getmChatId());
                Navigation.findNavController(mView)
                        .navigate(ChatListFragmentDirections
                                .actionNavigationChatListToNavigationChat());
            });
            binding.buttonLeave.setOnClickListener(view -> {
                mChatListViewModel.setChatId(chatroom.getChatId());
                //TODO : make UserInfoViewModel work here
                mChatListViewModel.deleteChat(mUserInfoViewModel.getmJwt(), chatroom.getChatId(), mUserInfoViewModel.getEmail());
                System.out.println(mUserInfoViewModel.getEmail());
            });
            binding.buttonEdit.setOnClickListener(view -> {
                mChatListViewModel.setChatId(chatroom.getChatId());
                System.out.println(mChatListViewModel.getmChatId());
                Navigation.findNavController(view).navigate(ChatListFragmentDirections.actionNavigationChatListToAddChatContactFragment());
            });
        }

        public Chatroom getChatroom() {
            return mChat;
        }


    }

    //public void setChatroomName(List<Chatroom> rooms) {
       // mChats = rooms;
    //}

}
