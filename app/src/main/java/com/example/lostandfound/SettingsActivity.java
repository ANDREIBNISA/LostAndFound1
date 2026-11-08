package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;

/** Screen 06: Settings. The five pages are written in the XML; the tabs choose which one is visible. */
public class SettingsActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navSettings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpNavigation();

        int[] tabIds = {R.id.tabAccount, R.id.tabSecurity, R.id.tabNotifications, R.id.tabPreferences, R.id.tabOther};
        int[] pageIds = {R.id.pageAccount, R.id.pageSecurity, R.id.pageNotifications, R.id.pagePreferences, R.id.pageOther};

        for (int i = 0; i < tabIds.length; i++) {
            final int chosen = i;
            findViewById(tabIds[i]).setOnClickListener(v -> {
                // Show only the page that matches the tab that was tapped.
                for (int j = 0; j < tabIds.length; j++) {
                    findViewById(tabIds[j]).setSelected(j == chosen);
                    findViewById(pageIds[j]).setVisibility(j == chosen ? View.VISIBLE : View.GONE);
                }
                hideKeyboard();
            });
        }
        // Start on "Account Information".
        findViewById(R.id.tabAccount).setSelected(true);

        findViewById(R.id.logoutButton).setOnClickListener(v -> logout());
    }
}
