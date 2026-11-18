package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Another person's profile. It opens when you tap the "Lost by / Found by" name on Item details,
 * or "Visit profile" in a chat. Below their details is a Message button that opens a chat with them.
 * Everything here is placeholder text made from the username; the database will provide real data later.
 */
public class UserProfileActivity extends NoSlideActivity {

    public static final String EXTRA_USERNAME = "profileUsername";

    // Items shown under "Recent reports": { name, small line under it }
    private static final String[][] SAMPLE_REPORTS = {
            {"Blue umbrella", "Lost  •  September 8, 2026"},
            {"Casio calculator", "Found  •  September 2, 2026"},
            {"Gray hoodie", "Lost  •  August 27, 2026"},
            {"Phone charger", "Found  •  August 19, 2026"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String username = getIntent().getStringExtra(EXTRA_USERNAME);
        if (username == null) {
            finish();
            return;
        }

        String displayName = ChatStore.displayNameOf(username);
        ((TextView) findViewById(R.id.userDisplayName)).setText(displayName);

        // When we only know the username, it is also the display name: show it once.
        TextView usernameText = findViewById(R.id.userUsername);
        if (displayName.equals(username)) {
            usernameText.setVisibility(View.GONE);
        } else {
            usernameText.setText(username);
        }

        // Placeholder numbers that stay the same for the same person.
        int seed = Math.abs(username.hashCode());
        ((TextView) findViewById(R.id.userStats)).setText(
                (3 + seed % 9) + " Reports   " + (seed % 5) + " Found   " + (seed % 4) + " Returned");

        ViewGroup list = findViewById(R.id.userReportsList);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < 2; i++) {
            String[] report = SAMPLE_REPORTS[(seed + i) % SAMPLE_REPORTS.length];
            View card = inflater.inflate(R.layout.item_profile_card, list, false);
            ((TextView) card.findViewById(R.id.profileCardName)).setText(report[0]);
            ((TextView) card.findViewById(R.id.profileCardMeta)).setText(report[1]);
            ((ImageView) card.findViewById(R.id.cardPicture)).setImageResource(ItemPictures.forName(report[0]));
            // Their own report cards open item details too.
            card.setOnClickListener(v -> openDetails(report));
            list.addView(card);
        }

        findViewById(R.id.backArrow).setOnClickListener(v -> finish());
        findViewById(R.id.messageUserButton).setOnClickListener(v -> {
            ChatStore.Chat chat = ChatStore.findOrCreate(username);
            Intent open = new Intent(this, ChatActivity.class);
            open.putExtra(ChatActivity.EXTRA_USERNAME, chat.username);
            startActivity(open);
        });
    }

    private void openDetails(String[] report) {
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(ItemDetailsActivity.EXTRA_NAME, report[0]);
        intent.putExtra(ItemDetailsActivity.EXTRA_KIND, report[1].startsWith("Lost") ? "Lost" : "Found");
        startActivity(intent);
    }
}
