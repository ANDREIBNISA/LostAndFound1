package com.example.lostandfound;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Makes the typing box and Send button of a conversation work. Used by the landscape Messages
 * screen and by ChatActivity (portrait). There is no database yet, so a sent message is only
 * shown as a new bubble on the right; it is gone when the screen closes.
 */
final class ChatHelper {

    private ChatHelper() {
    }

    /** Call once the layout is set. Does nothing if this layout has no typing box. */
    static void setUpSend(Activity activity) {
        EditText input = activity.findViewById(R.id.chatInput);
        View sendButton = activity.findViewById(R.id.sendButton);
        ViewGroup messages = activity.findViewById(R.id.chatMessages);
        View scroll = activity.findViewById(R.id.chatScroll);
        if (input == null || sendButton == null || messages == null) {
            return;
        }

        Runnable send = () -> {
            String text = input.getText().toString().trim();
            if (text.isEmpty()) {
                return;
            }
            // One copy of the XML design item_chat_bubble_mine.xml, with your words in it.
            View bubble = LayoutInflater.from(activity)
                    .inflate(R.layout.item_chat_bubble_mine, messages, false);
            ((TextView) bubble.findViewById(R.id.bubbleText)).setText(text);
            messages.addView(bubble);
            input.setText("");
            if (scroll != null) {
                scroll.post(() -> scroll.scrollTo(0, messages.getBottom()));
            }
        };

        sendButton.setOnClickListener(v -> send.run());
        input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                send.run();
                return true;
            }
            return false;
        });
    }
}
