package com.example.lostandfound;

import android.os.Bundle;

/** Screen 02: Home. The cards are written in the XML; this only wires the "More items" links. */
public class HomeActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navHome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpNavigation();

        // "High priority searches" rows lead to Lost items, "Recent report" rows to Report status.
        onClickOpen(R.id.moreItems1, R.id.navLost);
        onClickOpen(R.id.moreItems2, R.id.navStatus);
        onClickOpen(R.id.moreItems3, R.id.navLost);
        onClickOpen(R.id.moreItems4, R.id.navStatus);
    }
}
