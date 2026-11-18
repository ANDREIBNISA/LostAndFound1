package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Start a chat with anyone, including people you have never talked to. Type their username and
 * a first message, and the conversation opens. Nothing is sent anywhere yet (no database), so
 * the chat only lives in ChatStore while the app is open.
 */
public class NewMessageActivity extends NoSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        findViewById(R.id.backArrow).setOnClickListener(v -> finish());
        findViewById(R.id.sendNewMessageButton).setOnClickListener(v -> send());
    }

    private void send() {
        String to = textOf(R.id.toInput);
        String message = textOf(R.id.firstMessageInput);

        if (to.isEmpty() || message.isEmpty()) {
            toast("Please type who it is for, and your message");
            return;
        }
        if (to.replace("@", "").equalsIgnoreCase(ChatStore.MY_USERNAME)) {
            toast("That is your own username");
            return;
        }

        ChatStore.Chat chat = ChatStore.findOrCreate(to);
        ChatStore.send(chat, message);

        // Open the conversation, and close this screen so Back returns to the chat list.
        Intent open = new Intent(this, ChatActivity.class);
        open.putExtra(ChatActivity.EXTRA_USERNAME, chat.username);
        startActivity(open);
        finish();
    }

    /** The words typed into a text box, without spaces at the start or end. */
    private String textOf(int inputId) {
        EditText input = findViewById(inputId);
        return input.getText().toString().trim();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
