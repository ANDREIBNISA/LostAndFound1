package com.example.lostandfound;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Screen 06: Settings. The pages are written in the XML; the tabs choose which one is visible.
 * On the Account page a pencil turns a line into a box you can type in, and "Save changes"
 * only shows a message because there is no database yet.
 */
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

        // Gender is optional: tap one to pick it, tap it again to clear it.
        View male = findViewById(R.id.genderMale);
        View female = findViewById(R.id.genderFemale);
        male.setOnClickListener(v -> {
            male.setSelected(!male.isSelected());
            female.setSelected(false);
        });
        female.setOnClickListener(v -> {
            female.setSelected(!female.isSelected());
            male.setSelected(false);
        });

        findViewById(R.id.saveSettingsButton).setOnClickListener(v -> {
            hideKeyboard();
            toast("Changes saved (prototype: nothing is stored yet)");
        });
        findViewById(R.id.logoutButton).setOnClickListener(v -> logout());
    }

    /** The pencil at the end of a line (android:onClick in settings_pages.xml). */
    public void onEditPencilClick(View pencil) {
        // The line is a row: label, value box (tagged "value"), maybe a tag, then the pencil.
        ViewGroup row = (ViewGroup) pencil.getParent();
        EditText value = row.findViewWithTag("value");
        if (value == null) {
            return;
        }
        if (value.isEnabled()) {
            finishEditing(value);
            return;
        }
        startEditing(value);
    }

    private void startEditing(EditText value) {
        value.setEnabled(true);
        value.setBackgroundResource(R.drawable.bg_input_white);
        value.setTextColor(getColor(R.color.black));
        value.requestFocus();
        value.setSelection(value.getText().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(value, InputMethodManager.SHOW_IMPLICIT);
        }
        // Pressing Done on the keyboard ends the editing too.
        value.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                finishEditing(value);
                return true;
            }
            return false;
        });
    }

    private void finishEditing(EditText value) {
        value.setEnabled(false);
        value.setBackground(null);
        value.setTextColor(getColor(R.color.white));
        hideKeyboard();
    }

    public void onManagePasskeysClick(View button) {
        toast("Passkeys can be managed once accounts are connected");
    }

    public void onChangePasswordClick(View pencil) {
        toast("Changing your password works once accounts are connected");
    }
}
