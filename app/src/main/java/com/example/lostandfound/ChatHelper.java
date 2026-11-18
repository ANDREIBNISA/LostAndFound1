package com.example.lostandfound;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.Supplier;

/**
 * Shared by the landscape Messages screen and ChatActivity (portrait): shows the bubbles of a
 * conversation, makes the typing box and Send button work, and opens the drop-down that appears
 * when you tap the other person's picture (Visit profile, Block, Cancel). The bubble designs are
 * the XML files item_chat_bubble_theirs.xml and item_chat_bubble_mine.xml; this class makes one
 * copy per message.
 */
final class ChatHelper {

    private ChatHelper() {
    }

    /**
     * Show the name, username and every message of this chat. onChanged (may be null) runs when
     * something about the chat changes from the picture menu, for example after blocking.
     */
    static void showChat(Activity activity, ChatStore.Chat chat, Runnable onChanged) {
        TextView title = activity.findViewById(R.id.chatTitle);
        TextView username = activity.findViewById(R.id.chatUsername);
        if (title != null) {
            title.setText(chat.displayName);
        }
        if (username != null) {
            // A new user has no display name yet, so the name and username are the same: show it once.
            boolean same = chat.displayName.equals(chat.username);
            username.setText(same ? "" : "@" + chat.username);
            username.setVisibility(same ? View.GONE : View.VISIBLE);
        }

        View avatar = activity.findViewById(R.id.chatAvatar);
        if (avatar != null) {
            avatar.setOnClickListener(v -> showAvatarMenu(activity, v, chat, onChanged));
        }

        ViewGroup messages = activity.findViewById(R.id.chatMessages);
        if (messages != null) {
            messages.removeAllViews();
            for (ChatStore.Message message : chat.messages) {
                addBubble(activity, messages, message);
            }
            scrollToBottom(activity);
        }
        updateInputState(activity, chat);
    }

    /** The drop-down under the other person's picture: Visit profile, Block (or Unblock), Cancel. */
    private static void showAvatarMenu(Activity activity, View anchor, ChatStore.Chat chat, Runnable onChanged) {
        PopupMenu menu = new PopupMenu(activity, anchor);
        menu.getMenuInflater().inflate(R.menu.chat_avatar_menu, menu.getMenu());
        menu.getMenu().findItem(R.id.chatBlockToggle).setTitle(chat.blocked ? "Unblock" : "Block");

        menu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.chatVisitProfile) {
                Intent open = new Intent(activity, UserProfileActivity.class);
                open.putExtra(UserProfileActivity.EXTRA_USERNAME, chat.username);
                activity.startActivity(open);
            } else if (id == R.id.chatBlockToggle) {
                chat.blocked = !chat.blocked;
                Toast.makeText(activity,
                        (chat.blocked ? "Blocked " : "Unblocked ") + chat.displayName + " (prototype)",
                        Toast.LENGTH_SHORT).show();
                updateInputState(activity, chat);
                if (onChanged != null) {
                    onChanged.run();
                }
            }
            // "Cancel" does nothing: the drop-down closes and you are back in the chat.
            return true;
        });
        menu.show();
    }

    /** A blocked person cannot be messaged: grey out the typing box until you unblock them. */
    private static void updateInputState(Activity activity, ChatStore.Chat chat) {
        EditText input = activity.findViewById(R.id.chatInput);
        View sendButton = activity.findViewById(R.id.sendButton);
        if (input == null || sendButton == null) {
            return;
        }
        input.setEnabled(!chat.blocked);
        sendButton.setEnabled(!chat.blocked);
        input.setHint(chat.blocked ? "You blocked this user" : "Type a message");
    }

    private static void addBubble(Activity activity, ViewGroup messages, ChatStore.Message message) {
        int design = message.mine ? R.layout.item_chat_bubble_mine : R.layout.item_chat_bubble_theirs;
        View bubble = LayoutInflater.from(activity).inflate(design, messages, false);
        ((TextView) bubble.findViewById(R.id.bubbleText)).setText(message.text);
        messages.addView(bubble);
    }

    private static void scrollToBottom(Activity activity) {
        View scroll = activity.findViewById(R.id.chatScroll);
        if (scroll != null) {
            scroll.post(() -> scroll.scrollTo(0, Integer.MAX_VALUE / 2));
        }
    }

    /**
     * Make Send add your typed words to the chat that is open right now. Does nothing if this
     * layout has no typing box. afterSend runs once a message was added (to refresh a list).
     */
    static void setUpSend(Activity activity, Supplier<ChatStore.Chat> openChat, Runnable afterSend) {
        EditText input = activity.findViewById(R.id.chatInput);
        View sendButton = activity.findViewById(R.id.sendButton);
        ViewGroup messages = activity.findViewById(R.id.chatMessages);
        if (input == null || sendButton == null || messages == null) {
            return;
        }

        Runnable send = () -> {
            String text = input.getText().toString().trim();
            ChatStore.Chat chat = openChat.get();
            if (text.isEmpty() || chat == null || chat.blocked) {
                return;
            }
            ChatStore.send(chat, text);
            addBubble(activity, messages, chat.messages.get(chat.messages.size() - 1));
            input.setText("");
            scrollToBottom(activity);
            if (afterSend != null) {
                afterSend.run();
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
