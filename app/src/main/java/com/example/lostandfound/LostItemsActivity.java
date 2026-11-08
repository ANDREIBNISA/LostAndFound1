package com.example.lostandfound;

import android.os.Bundle;

/** Lost items list. Everything on screen is in activity_lost_items.xml. */
public class LostItemsActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navLost;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_items);
        setUpNavigation();
    }
}
