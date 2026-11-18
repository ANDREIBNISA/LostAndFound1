package com.example.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


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
public abstract class BaseActivity extends NoSlideActivity {

    /** Which button belongs to the screen you are on, for example R.id.navHome. */
    protected abstract int currentNavId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** Call this right after setContentView(...). */
    protected void setUpNavigation() {
        setUpScreenButtons();

        // Portrait: the avatar and name open your Profile. Landscape keeps opening Settings.
        boolean portrait = findViewById(R.id.bottomNav) != null;
        onClickOpen(R.id.profileRow, portrait ? R.id.navProfile : R.id.navSettings);
        setUpItemsTabs();
        fillItemCards();

        setUpMoreMenu();
        setUpSearch();
    }

    // ---------- Screen buttons: bottom bar (portrait) or sidebar (landscape) ----------

    private void setUpScreenButtons() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (bottomNav != null) {
            // Portrait: the bottom bar.
            Menu menu = bottomNav.getMenu();
            int tabId = portraitTabId();
            if (menu.findItem(tabId) != null) {
                bottomNav.setSelectedItemId(tabId);
            } else {
                // This screen has no tab of its own, so show all five as gray.
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

    /**
     * Which of the five portrait tabs lights up on this screen. Lost items and Found items
     * are both the "Items" tab, Make a report belongs to "Reports", Settings to "Profile".
     */
    private int portraitTabId() {
        int id = currentNavId();
        if (id == R.id.navLost || id == R.id.navFound) {
            return R.id.navItems;
        }
        if (id == R.id.navReport) {
            return R.id.navStatus;
        }
        if (id == R.id.navSettings) {
            return R.id.navProfile;
        }
        return id;
    }

    /** The Lost / Found tabs at the top of the portrait Items screens (the XML has them only in portrait). */
    private void setUpItemsTabs() {
        View lostTab = findViewById(R.id.itemsTabLost);
        View foundTab = findViewById(R.id.itemsTabFound);
        if (lostTab == null || foundTab == null) {
            return;
        }
        lostTab.setSelected(currentNavId() == R.id.navLost);
        foundTab.setSelected(currentNavId() == R.id.navFound);
        lostTab.setOnClickListener(v -> openScreen(R.id.navLost));
        foundTab.setOnClickListener(v -> openScreen(R.id.navFound));
    }

    /** The three dots in the portrait header: Settings and Log out. */
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
        } else if (navId == R.id.navProfile) {
            target = ProfileActivity.class;
        } else if (navId == R.id.navLost || navId == R.id.navItems) {
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
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(ItemDetailsActivity.EXTRA_KIND, currentNavId() == R.id.navFound ? "Found" : "Lost");
        // Pass on the words shown on the card, so the details page matches what was tapped.
        TextView name = card.findViewById(R.id.cardItemName);
        TextView by = card.findViewById(R.id.cardItemBy);
        if (name != null && by != null) {
            intent.putExtra(ItemDetailsActivity.EXTRA_NAME, valueAfterColon(name.getText().toString()));
            String byText = by.getText().toString();
            intent.putExtra(ItemDetailsActivity.EXTRA_KIND, byText.startsWith("Found") ? "Found" : "Lost");
            intent.putExtra(ItemDetailsActivity.EXTRA_BY, valueAfterColon(byText));
        }
        // Cards on the Profile screen have their own text views.
        TextView profileName = card.findViewById(R.id.profileCardName);
        TextView profileMeta = card.findViewById(R.id.profileCardMeta);
        if (profileName != null && profileMeta != null) {
            intent.putExtra(ItemDetailsActivity.EXTRA_NAME, profileName.getText().toString());
            intent.putExtra(ItemDetailsActivity.EXTRA_KIND,
                    profileMeta.getText().toString().startsWith("Lost") ? "Lost" : "Found");
        }
        startActivity(intent);
    }

    // ---------- Placeholder text on the item cards ----------

    // Placeholder items. When the database arrives, the cards will show real reports instead.
    private static final String[] LOST_NAMES = {
            "Black leather wallet", "Blue umbrella", "Silver water bottle", "Casio calculator",
            "House keys with red tag", "AirPods case", "Gray hoodie", "Math notebook",
            "Phone charger", "Prescription glasses", "Student ID card", "iPair 67 pro max 64tb",
            "rtx4090", "Green lunchbox", "ryzen 9 9980x3d", "Black backpack",
            "Wristwatch", "Gaming mouse", "Over-ear headphones", "White sneakers",
            "USB flash drive", "Navy baseball cap", "Science textbook", "Silver laptop",
            "Pencil case"};
    private static final String[] FOUND_NAMES = {
            "Black umbrella", "Red water bottle", "Scientific calculator", "Set of keys",
            "White earbuds", "Blue jacket", "Science notebook", "Laptop charger",
            "Brown wallet", "Reading glasses", "Library card", "128gb ddr5 ram",
            "Pencil case", "Yellow lunchbox", "1,000$ worth of cash", "Gray backpack",
            "Silver watch", "Wireless mouse", "Bluetooth headphones", "Running shoes",
            "Flash drive 1tb", "Red cap", "History book", "Black laptop",
            "Samsung Galaxy phone"};
    private static final String[] USERS = {
            "User67", "User12", "User88", "User45", "User203", "User31", "User76", "User90",
            "User18", "User55", "User402", "User27", "User64", "User09", "User83"};

    /**
     * The cards are copies of one XML design, so each one starts with "Item:" and "Lost by: User67".
     * This gives every card on the screen its own placeholder item name and user.
     */
    private void fillItemCards() {
        List<TextView> names = new ArrayList<>();
        List<TextView> bys = new ArrayList<>();
        collectCardTexts(findViewById(android.R.id.content), names, bys);

        for (int i = 0; i < names.size() && i < bys.size(); i++) {
            String byText = bys.get(i).getText().toString();
            boolean found = byText.startsWith("Found");
            String[] pool = found ? FOUND_NAMES : LOST_NAMES;
            // Spread the names around so neighbours differ, and the same card is the same each time.
            int pick = (i * 7 + (found ? 3 : 0)) % pool.length;
            names.get(i).setText("Item: " + pool[pick]);
            bys.get(i).setText((found ? "Found by: " : "Lost by: ") + USERS[(i * 7 + pick) % USERS.length]);
            showCardPicture(names.get(i), pool[pick]);
        }
    }

    /** Put the photo that matches the item name into the card this name belongs to. */
    private void showCardPicture(View nameView, String itemName) {
        // Climb to the card itself (the tappable block), then look for its picture. Tiles have none.
        View card = nameView;
        while (card.getParent() instanceof View && !card.hasOnClickListeners()) {
            card = (View) card.getParent();
        }
        ImageView picture = card.findViewById(R.id.cardPicture);
        if (picture != null) {
            picture.setImageResource(ItemPictures.forName(itemName));
        }
    }

    private void collectCardTexts(View view, List<TextView> names, List<TextView> bys) {
        if (view.getId() == R.id.cardItemName) {
            names.add((TextView) view);
        } else if (view.getId() == R.id.cardItemBy) {
            bys.add((TextView) view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                collectCardTexts(group.getChildAt(i), names, bys);
            }
        }
    }

    private String valueAfterColon(String text) {
        int colon = text.indexOf(':');
        return colon < 0 ? text : text.substring(colon + 1).trim();
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
