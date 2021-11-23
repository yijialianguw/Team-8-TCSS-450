package edu.uw.tcss450.messaging_final_project.ui.chat;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.messaging_final_project.R;
import edu.uw.tcss450.messaging_final_project.databinding.FragmentChatCardBinding;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatViewHolder>{

    private List<Chatroom> mChats;
    private final Map<Chatroom, Boolean> mExpandedFlags;
    private final ChatListFragment chatListFragment;

    public ChatListRecyclerViewAdapter(List<Chatroom> chats, ChatListFragment frag) {
        this.mChats = chats;
        this.chatListFragment = frag;
        mExpandedFlags = mChats.stream().collect(Collectors.toMap(Function.identity(), blog->false));
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChatroom(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private Chatroom mChat;
        private TextView textView;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(mView);

        }


        void setChatroom(final Chatroom chat) {
            mChat = chat;
            textView.setText(mChat.getChatName());
            binding.buttonOpen.setOnClickListener(view ->
                    Navigation.findNavController(mView)
                            .navigate(ChatListFragmentDirections
                                    .actionNavigationChatListToNavigationChat()));
        }
    }

    public void setChatroom(List<Chatroom> rooms) {
        mChats = rooms;
    }

}
