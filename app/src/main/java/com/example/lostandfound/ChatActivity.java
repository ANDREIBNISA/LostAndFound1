package com.example.lostandfound;

import android.os.Bundle;


/**
 * One conversation on the whole screen. It opens from a row in the chat list (portrait) or after
 * a new message is sent. The Back arrow returns to the list. The messages come from ChatStore.
 */
public class ChatActivity extends NoSlideActivity {

    public static final String EXTRA_USERNAME = "chatUsername";

    private ChatStore.Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat = ChatStore.find(getIntent().getStringExtra(EXTRA_USERNAME));
        if (chat == null) {
            finish();
            return;
        }
        ChatHelper.showChat(this, chat, null);

        findViewById(R.id.backArrow).setOnClickListener(v -> finish());
        ChatHelper.setUpSend(this, () -> chat, null);
    }
}
