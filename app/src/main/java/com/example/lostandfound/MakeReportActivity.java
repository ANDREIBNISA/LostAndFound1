package com.example.lostandfound;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

/** Screen 03: Make a report, step 1 of 3. */
public class MakeReportActivity extends BaseActivity {

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
            ((EditText) findViewById(R.id.emailInput)).setText("juan.delacruz@email.com");
            ((EditText) findViewById(R.id.mobileInput)).setText("09123456789");
            ((EditText) findViewById(R.id.alternateContactInput)).setText("facebook.com/juan.delacruz");
            toast("Details imported from settings");
        });

        // Steps 2 and 3 are not designed yet, so Next Step "submits" and shows the status list.
        findViewById(R.id.nextStepButton).setOnClickListener(v -> {
            hideKeyboard();
            toast("Report submitted (prototype)");
            openScreen(R.id.navStatus);
        });
    }
}
