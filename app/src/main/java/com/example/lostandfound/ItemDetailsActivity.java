package com.example.lostandfound;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Item details: opens when you tap an item card. All the text is placeholder.
 * The Intent says if the card was a lost item or a found item.
 */
public class ItemDetailsActivity extends BaseActivity {

    public static final String EXTRA_KIND = "kind";

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
        ((TextView) findViewById(R.id.detailsKind)).setText(kind);
        ((TextView) findViewById(R.id.detailsBy)).setText(kind + " by: User67");

        findViewById(R.id.messageOwnerButton).setOnClickListener(v -> openScreen(R.id.navMessages));
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
