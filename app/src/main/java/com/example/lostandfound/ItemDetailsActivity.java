package com.example.lostandfound;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Item details: opens when you tap an item card. The name, who posted it and whether it is
 * lost or found come from the card; the rest of the text is placeholder.
 */
public class ItemDetailsActivity extends BaseActivity {

    public static final String EXTRA_KIND = "kind";
    public static final String EXTRA_NAME = "itemName";
    public static final String EXTRA_BY = "itemBy";

    private boolean isFound() {
        return "Found".equals(getIntent().getStringExtra(EXTRA_KIND));
    }

    @Override
    protected int currentNavId() {
        // The Items tab stays lit, on the Lost or the Found side.
        return isFound() ? R.id.navFound : R.id.navLost;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        setUpNavigation();

        String kind = isFound() ? "Found" : "Lost";
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String by = getIntent().getStringExtra(EXTRA_BY);

        ((TextView) findViewById(R.id.detailsKind)).setText(kind);
        if (name != null) {
            ((TextView) findViewById(R.id.detailsName)).setText(name);
        }
        ((ImageView) findViewById(R.id.detailsPicture)).setImageResource(ItemPictures.forName(name));

        // "Lost by:" then the person's name, underlined. Tapping it opens their profile.
        String username = by == null ? "User67" : by;
        ((TextView) findViewById(R.id.detailsByLabel)).setText(kind + " by:");
        TextView byUser = findViewById(R.id.detailsByUser);
        byUser.setPaintFlags(byUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        byUser.setText(ChatStore.displayNameOf(username));
        byUser.setOnClickListener(v -> {
            Intent open = new Intent(this, UserProfileActivity.class);
            open.putExtra(UserProfileActivity.EXTRA_USERNAME, username);
            startActivity(open);
        });

        findViewById(R.id.messageOwnerButton).setOnClickListener(v -> openScreen(R.id.navMessages));
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
