package com.example.lostandfound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Profile screen. The layout (content_profile.xml) and the card design (item_profile_card.xml)
 * are in XML. This class only switches between the four icon tabs and makes one copy of the
 * card design per placeholder item. When the database arrives, the lists will come from it.
 */
public class ProfileActivity extends BaseActivity {

    private static final String[] TAB_TITLES = {"My reports", "Found items", "Returned items", "Saved"};

    // For every tab: { item name, small line under it }. Saved is empty on purpose, to show the empty message.
    private static final String[][][] TAB_ITEMS = {
            {
                    {"Black leather wallet", "Lost  •  September 11, 2026"},
                    {"128gb ddr5 ram", "Lost  •  September 11, 2026"},
                    {"ryzen 9 9980x3d", "Lost  •  September 11, 2026"},
            },
            {
                    {"iPair 67 pro max 64tb", "Found  •  September 11, 2026"},
                    {"rtx4090", "Found  •  August 20, 2026"},
            },
            {
                    {"1,000$ worth of cash", "Returned  •  September 4, 2026"},
                    {"Black leather wallet", "Returned  •  August 29, 2026"},
            },
            {},
    };

    private static final String[] EMPTY_MESSAGES = {
            "You have no reports yet.",
            "You have not found anything yet.",
            "Nothing has been returned yet.",
            "No saved items yet.",
    };

    private ViewGroup list;
    private TextView emptyMessage;
    private TextView tabTitle;
    private View[] tabs;

    @Override
    protected int currentNavId() {
        return R.id.navProfile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpNavigation();

        list = findViewById(R.id.profileList);
        emptyMessage = findViewById(R.id.profileEmpty);
        tabTitle = findViewById(R.id.profileTabTitle);
        tabs = new View[]{
                findViewById(R.id.profileTab1), findViewById(R.id.profileTab2),
                findViewById(R.id.profileTab3), findViewById(R.id.profileTab4)
        };

        for (int i = 0; i < tabs.length; i++) {
            final int chosen = i;
            tabs[i].setOnClickListener(v -> showTab(chosen));
        }
        showTab(0);

        onClickOpen(R.id.editProfileButton, R.id.navSettings);
        findViewById(R.id.closedReportsButton).setOnClickListener(v -> {
            toast("Closed reports are in the Previous reports list");
            openScreen(R.id.navStatus);
        });
    }

    private void showTab(int chosen) {
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setSelected(i == chosen);
        }
        tabTitle.setText(TAB_TITLES[chosen]);

        list.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String[] item : TAB_ITEMS[chosen]) {
            View card = inflater.inflate(R.layout.item_profile_card, list, false);
            ((TextView) card.findViewById(R.id.profileCardName)).setText(item[0]);
            ((TextView) card.findViewById(R.id.profileCardMeta)).setText(item[1]);
            ((ImageView) card.findViewById(R.id.cardPicture)).setImageResource(ItemPictures.forName(item[0]));
            list.addView(card);
        }

        boolean empty = TAB_ITEMS[chosen].length == 0;
        emptyMessage.setText(EMPTY_MESSAGES[chosen]);
        emptyMessage.setVisibility(empty ? View.VISIBLE : View.GONE);
    }
}
