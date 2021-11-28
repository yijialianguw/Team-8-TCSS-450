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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatCardBinding;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatViewHolder>{

    private Context context;
    private ArrayList<Chatroom> mChats;
    //private final Map<Chatroom, Boolean> mExpandedFlags;
    //private final ChatListFragment chatListFragment;
    private ChatListViewModel mChatListViewModel;

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

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView chatIV;
        public FragmentChatCardBinding binding;
        private Chatroom mChat;
        private TextView chatTV;
        private View mView;

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
                Navigation.findNavController(mView)
                        .navigate(ChatListFragmentDirections
                                .actionNavigationChatListToNavigationChat());
            });
        }


    }

    //public void setChatroomName(List<Chatroom> rooms) {
       // mChats = rooms;
    //}

}
