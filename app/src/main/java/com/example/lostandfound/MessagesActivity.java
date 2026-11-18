package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Screen 05: Messages. The four chats and the conversation bubbles are written in the XML
 * (the bubbles are in include_chat_bubbles.xml).
 *
 * Landscape: the conversation is on the right of the list, so tapping a chat only switches it.
 * Portrait: tapping a chat opens it full screen in ChatActivity, like a messaging app.
 * Each chat row carries its latest message in its android:tag, and its name TextView is tagged "name".
 */
public class MessagesActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navMessages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        setUpNavigation();

        int[] rowIds = {R.id.chatRow1, R.id.chatRow2, R.id.chatRow3, R.id.chatRow4};
        View[] rows = new View[rowIds.length];
        for (int i = 0; i < rowIds.length; i++) {
            rows[i] = findViewById(rowIds[i]);
        }

        TextView chatTitle = findViewById(R.id.chatTitle);
        TextView chatBubble = findViewById(R.id.chatBubble);

        if (chatTitle == null || chatBubble == null) {
            // Portrait: this screen is only the list.
            for (View row : rows) {
                row.setOnClickListener(v -> {
                    TextView name = row.findViewWithTag("name");
                    Intent chat = new Intent(this, ChatActivity.class);
                    chat.putExtra(ChatActivity.EXTRA_NAME, name.getText().toString());
                    startActivity(chat);
                });
            }
            return;
        }

        for (View row : rows) {
            row.setOnClickListener(v -> {
                // Highlight only the chat that was tapped.
                for (View other : rows) {
                    other.setSelected(other == row);
                }
                // Show that person's name on top of the conversation and their message in the first bubble.
                TextView name = row.findViewWithTag("name");
                chatTitle.setText(name.getText());
                chatBubble.setText((String) row.getTag());
            });
        }
        // As in the design, the first chat starts open.
        rows[0].performClick();

        ChatHelper.setUpSend(this);
    }
}
