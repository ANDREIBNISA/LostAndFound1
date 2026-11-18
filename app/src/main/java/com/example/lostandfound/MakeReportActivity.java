package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Screen 03: Making a report, step 1 of 3 (item details).
 * Every box must be filled in. Step 2 and step 3 follow, and the typed words travel
 * from screen to screen inside the Intent (see the EXTRA_ names below).
 */
public class MakeReportActivity extends BaseActivity {

    public static final String EXTRA_ITEM_NAME = "itemName";
    public static final String EXTRA_KIND = "kind";
    public static final String EXTRA_OWNER = "owner";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_MOBILE = "mobile";
    public static final String EXTRA_ALTERNATE = "alternate";

    @Override
    protected int currentNavId() {
        return R.id.navReport;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_report);
        setUpNavigation();

        CheckBox lostCheck = findViewById(R.id.lostItemCheck);
        CheckBox foundCheck = findViewById(R.id.foundItemCheck);
        // An item is either lost or found, so ticking one un-ticks the other.
        lostCheck.setOnCheckedChangeListener((button, checked) -> {
            if (checked) {
                foundCheck.setChecked(false);
            }
        });
        foundCheck.setOnCheckedChangeListener((button, checked) -> {
            if (checked) {
                lostCheck.setChecked(false);
            }
        });

        // Placeholder "saved" details, since there is no account data yet.
        findViewById(R.id.importDetailsButton).setOnClickListener(v -> {
            ((EditText) findViewById(R.id.emailInput)).setText("johndoe26@email.com");
            ((EditText) findViewById(R.id.mobileInput)).setText("09123456789");
            ((EditText) findViewById(R.id.alternateContactInput)).setText("@johndoe26");
            toast("Details imported from settings");
        });

        findViewById(R.id.nextStepButton).setOnClickListener(v -> {
            hideKeyboard();
            goToStep2(lostCheck, foundCheck);
        });
    }

    private void goToStep2(CheckBox lostCheck, CheckBox foundCheck) {
        String itemName = textOf(R.id.itemNameInput);
        String owner = textOf(R.id.ownerInput);
        String location = textOf(R.id.locationInput);
        String email = textOf(R.id.emailInput);
        String mobile = textOf(R.id.mobileInput);
        String alternate = textOf(R.id.alternateContactInput);

        // Every box must be filled in, so the person who reads the report has what they need.
        if (itemName.isEmpty() || owner.isEmpty() || location.isEmpty()
                || email.isEmpty() || mobile.isEmpty() || alternate.isEmpty()) {
            toast("Please fill in every box so people can reach you");
            return;
        }
        if (!lostCheck.isChecked() && !foundCheck.isChecked()) {
            toast("Please tick Lost item or Found item");
            return;
        }

        Intent next = new Intent(this, ReportStep2Activity.class);
        next.putExtra(EXTRA_ITEM_NAME, itemName);
        next.putExtra(EXTRA_KIND, lostCheck.isChecked() ? "Lost" : "Found");
        next.putExtra(EXTRA_OWNER, owner);
        next.putExtra(EXTRA_LOCATION, location);
        next.putExtra(EXTRA_EMAIL, email);
        next.putExtra(EXTRA_MOBILE, mobile);
        next.putExtra(EXTRA_ALTERNATE, alternate);
        startActivity(next);
    }

    /** The words typed into a text box, without spaces at the start or end. */
    private String textOf(int inputId) {
        EditText input = findViewById(inputId);
        return input.getText().toString().trim();
    }
}
