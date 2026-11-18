package com.example.lostandfound;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * One conversation on the whole screen (portrait). It opens from a row in the chat list and
 * the Back arrow returns to the list. The bubbles are placeholder text in the XML.
 */
public class ChatActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "chatName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        ((TextView) findViewById(R.id.chatTitle)).setText(name == null ? "" : name);

        findViewById(R.id.backArrow).setOnClickListener(v -> finish());
        ChatHelper.setUpSend(this);
    }
}
