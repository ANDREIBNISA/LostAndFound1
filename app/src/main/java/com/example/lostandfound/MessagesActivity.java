package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Screen 05: Messages. The chat list is a copy of the XML design item_chat_row.xml for every chat
 * in ChatStore (display name first, then the username, then the latest message).
 *
 * Landscape: the open conversation is on the right of the list, so tapping a chat only switches it.
 * Portrait: tapping a chat opens it full screen in ChatActivity, like a messaging app.
 * The + button starts a message to a new user (NewMessageActivity).
 */
public class MessagesActivity extends BaseActivity {

    private ViewGroup chatList;
    private ChatStore.Chat openChat;   // landscape only: the chat shown on the right

    @Override
    protected int currentNavId() {
        return R.id.navMessages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        setUpNavigation();

        chatList = findViewById(R.id.chatList);
        findViewById(R.id.newMessageButton).setOnClickListener(v ->
                startActivity(new Intent(this, NewMessageActivity.class)));

        // Only the landscape layout has the conversation on the right.
        boolean landscape = findViewById(R.id.chatMessages) != null;
        if (landscape) {
            openChat = ChatStore.all().get(0);
            ChatHelper.showChat(this, openChat, this::showChatList);
            ChatHelper.setUpSend(this, () -> openChat, this::showChatList);
        }
        showChatList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // A message may have been sent or started on another screen, so draw the list again.
        if (chatList != null) {
            showChatList();
        }
    }

    /** Draw one row per chat, newest message first. */
    private void showChatList() {
        chatList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        boolean landscape = findViewById(R.id.chatMessages) != null;

        for (ChatStore.Chat chat : ChatStore.all()) {
            if (chat.messages.isEmpty()) {
                continue;   // a chat with nobody written to yet stays out of the list
            }
            View row = inflater.inflate(R.layout.item_chat_row, chatList, false);
            ((TextView) row.findViewById(R.id.rowName)).setText(chat.displayName);

            TextView rowUsername = row.findViewById(R.id.rowUsername);
            boolean same = chat.displayName.equals(chat.username);
            rowUsername.setText("@" + chat.username);
            rowUsername.setVisibility(same ? View.GONE : View.VISIBLE);

            ((TextView) row.findViewById(R.id.rowPreview)).setText(chat.preview());
            row.setSelected(landscape && chat == openChat);

            row.setOnClickListener(v -> {
                if (landscape) {
                    openChat = chat;
                    ChatHelper.showChat(this, chat, this::showChatList);
                    showChatList();
                } else {
                    Intent open = new Intent(this, ChatActivity.class);
                    open.putExtra(ChatActivity.EXTRA_USERNAME, chat.username);
                    startActivity(open);
                }
            });
            chatList.addView(row);
        }
    }
}
