package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Screen 05: Messages. The four chats are written in the XML.
 * Each chat row carries its full message in its android:tag, and its name TextView is tagged "name".
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

        for (View row : rows) {
            row.setOnClickListener(v -> {
                // Highlight only the chat that was tapped and show its message.
                for (View other : rows) {
                    other.setSelected(other == row);
                }
                TextView name = row.findViewWithTag("name");
                chatTitle.setText(name.getText());
                chatBubble.setText((String) row.getTag());
                chatTitle.setVisibility(View.VISIBLE);
                chatBubble.setVisibility(View.VISIBLE);
            });
        }
        // In the design the first chat starts highlighted, with an empty conversation area.
        rows[0].setSelected(true);
    }
}
