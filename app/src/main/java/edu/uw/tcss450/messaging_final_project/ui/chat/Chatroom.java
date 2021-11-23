package edu.uw.tcss450.messaging_final_project.ui.chat;

import java.io.Serializable;

public class Chatroom implements Serializable {

    private int mChatId;
    private String mChatName;

    public Chatroom(int chatIdNum, String chatName) {
        mChatId = chatIdNum;
        mChatName = chatName;
    }

    public int getChatId() {
        return mChatId;
    }

    public String getChatName() {
        return mChatName;
    }
}