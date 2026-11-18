package com.example.lostandfound;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The base of every screen in the app. It turns off the slide animation Android plays when one
 * screen opens or closes, so tapping a tab or a button switches screens at once, without
 * looking like the Back button was pressed.
 *
 * Android 14 and newer need overrideActivityTransition; older versions use overridePendingTransition.
 */
public abstract class NoSlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeSlide();
    }

    @Override
    public void finish() {
        super.finish();
        removeSlide();
    }

    private void removeSlide() {
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0);
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, 0);
        } else {
            overridePendingTransition(0, 0);
        }
    }
}
