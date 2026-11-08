package com.example.lostandfound;

import android.os.Bundle;

/** Found items list. Everything on screen is in activity_found_items.xml. */
public class FoundItemsActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navFound;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_items);
        setUpNavigation();
    }
}
