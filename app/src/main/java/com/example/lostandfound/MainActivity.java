package com.example.lostandfound;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

// Sample-screen launcher: it only swaps between the three XML screens
// so they can be seen on the emulator. There is no real login or data yet.
public class MainActivity extends AppCompatActivity {

    // 0 = login, 1 = home, 2 = lost and found list
    private int currentScreen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogin();

        // The phone's Back button goes one screen back instead of closing the app.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (currentScreen == 2) {
                    showHome();
                } else if (currentScreen == 1) {
                    showLogin();
                } else {
                    finish();
                }
            }
        });
    }

    private void showLogin() {
        currentScreen = 0;
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> showHome());
    }

    private void showHome() {
        currentScreen = 1;
        setContentView(R.layout.activity_home);

        Button reportedItemsButton = findViewById(R.id.reportedItemsButton);
        reportedItemsButton.setOnClickListener(v -> showLostFound());

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> showLogin());
    }

    private void showLostFound() {
        currentScreen = 2;
        setContentView(R.layout.activity_lost_found);
    }
}
