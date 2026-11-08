package com.example.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The parts every screen after Login shares.
 *
 * Every screen has its own XML layout, and each layout comes in two versions:
 *  - res/layout       : portrait (phone held upright): header on top, bottom bar at the bottom
 *  - res/layout-land  : landscape (phone on its side): top bar and a left sidebar
 * Android picks the right one by itself when the phone is turned.
 *
 * The buttons that open other screens have the same ids in both versions
 * (navHome, navLost, ...), so the code here works for both.
 *
 * There is no database or real login yet, so all the names and items are
 * placeholder text typed into the XML, and the buttons only change screens.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /** Which button belongs to the screen you are on, for example R.id.navHome. */
    protected abstract int currentNavId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** Call this right after setContentView(...). */
    protected void setUpNavigation() {
        setUpScreenButtons();

        // Tapping the avatar and name opens Settings.
        onClickOpen(R.id.profileRow, R.id.navSettings);

        setUpMoreMenu();
        setUpSearch();
    }

    // ---------- Screen buttons: bottom bar (portrait) or sidebar (landscape) ----------

    private void setUpScreenButtons() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (bottomNav != null) {
            // Portrait: the bottom bar.
            Menu menu = bottomNav.getMenu();
            if (menu.findItem(currentNavId()) != null) {
                bottomNav.setSelectedItemId(currentNavId());
            } else {
                // Report status and Messages are not in the bottom bar, so show all five as gray.
                ColorStateList gray = ColorStateList.valueOf(getColor(R.color.fi_nav_gray));
                bottomNav.setItemIconTintList(gray);
                bottomNav.setItemTextColor(gray);
            }
            bottomNav.setOnItemSelectedListener(item -> {
                openScreen(item.getItemId());
                return false;
            });
        } else {
            // Landscape: the sidebar. The current screen's button is turned the darker blue.
            int[] sidebarButtons = {
                    R.id.navHome, R.id.navLost, R.id.navFound, R.id.navReport,
                    R.id.navStatus, R.id.navMessages, R.id.navSettings
            };
            for (int id : sidebarButtons) {
                View button = findViewById(id);
                if (button != null) {
                    button.setSelected(id == currentNavId());
                    button.setOnClickListener(v -> openScreen(id));
                }
            }
        }
    }

    /** The three dots in the portrait header: the screens that are not in the bottom bar. */
    private void setUpMoreMenu() {
        View moreButton = findViewById(R.id.moreButton);
        if (moreButton == null) {
            return;
        }
        moreButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                openScreen(item.getItemId());
                return true;
            });
            popup.show();
        });
    }

    // ---------- Search box (no real search yet: it opens the Lost items list) ----------

    private void setUpSearch() {
        EditText searchInput = findViewById(R.id.searchInput);
        View searchIcon = findViewById(R.id.searchIcon);
        if (searchInput == null) {
            return;
        }
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                runSearch(searchInput);
                return true;
            }
            return false;
        });
        if (searchIcon != null) {
            searchIcon.setOnClickListener(v -> runSearch(searchInput));
        }
    }

    private void runSearch(EditText searchInput) {
        String words = searchInput.getText().toString().trim();
        if (!words.isEmpty()) {
            toast("Searching for \"" + words + "\" (prototype: showing all lost items)");
        }
        hideKeyboard();
        openScreen(R.id.navLost);
    }

    // ---------- Opening screens ----------

    /** Open the screen that belongs to a button id such as R.id.navHome. */
    protected void openScreen(int navId) {
        Class<?> target = null;
        if (navId == R.id.navHome) {
            target = HomeActivity.class;
        } else if (navId == R.id.navLost) {
            target = LostItemsActivity.class;
        } else if (navId == R.id.navFound) {
            target = FoundItemsActivity.class;
        } else if (navId == R.id.navReport) {
            target = MakeReportActivity.class;
        } else if (navId == R.id.navStatus) {
            target = ReportStatusActivity.class;
        } else if (navId == R.id.navMessages) {
            target = MessagesActivity.class;
        } else if (navId == R.id.navSettings) {
            target = SettingsActivity.class;
        } else if (navId == R.id.navLogout) {
            logout();
            return;
        }

        if (target == null || target == getClass()) {
            return;
        }
        Intent intent = new Intent(this, target);
        // If that screen is already open further back, go back to it instead of stacking another copy.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    /** Back to the Login screen and forget every screen that was open. */
    protected void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /** Make the view with this id open a screen when tapped (does nothing if this layout has no such view). */
    protected void onClickOpen(int viewId, int navId) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(v -> openScreen(navId));
        }
    }

    /** Called by the item cards in the XML (android:onClick="onItemCardClick"). */
    public void onItemCardClick(View card) {
        toast("Item details are not built yet");
    }

    // ---------- Small helpers ----------

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyboard() {
        View focused = getCurrentFocus();
        if (focused != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(focused.getWindowToken(), 0);
            }
            focused.clearFocus();
        }
    }
}
