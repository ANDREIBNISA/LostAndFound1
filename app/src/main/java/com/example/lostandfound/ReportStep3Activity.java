package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Making a report, step 3 of 3: review what was typed in steps 1 and 2, then "submit".
 * Nothing is saved anywhere yet. Each line is a copy of the XML design row_review.xml.
 */
public class ReportStep3Activity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navReport;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_step3);
        setUpNavigation();

        ViewGroup list = findViewById(R.id.reviewList);
        addLine(list, "This item is", MakeReportActivity.EXTRA_KIND);
        addLine(list, "Item name", MakeReportActivity.EXTRA_ITEM_NAME);
        addLine(list, "Item owner or finder", MakeReportActivity.EXTRA_OWNER);
        addLine(list, "Lost/Found at", MakeReportActivity.EXTRA_LOCATION);
        addLine(list, "Date", ReportStep2Activity.EXTRA_DATE);
        addLine(list, "Category", ReportStep2Activity.EXTRA_CATEGORY);
        addLine(list, "Description", ReportStep2Activity.EXTRA_DESCRIPTION);
        addLine(list, "Photo", ReportStep2Activity.EXTRA_PHOTO);
        addLine(list, "Email", MakeReportActivity.EXTRA_EMAIL);
        addLine(list, "Mobile number", MakeReportActivity.EXTRA_MOBILE);
        addLine(list, "Alternate contact", MakeReportActivity.EXTRA_ALTERNATE);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.submitButton).setOnClickListener(v -> {
            toast("Report submitted (prototype: nothing is saved yet)");
            // Go back to Home first, which closes the three report steps, then open Reports on top of it.
            Intent home = new Intent(this, HomeActivity.class);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(home);
            openScreen(R.id.navStatus);
        });
    }

    /** One copy of row_review.xml: a label above the value that came in the Intent. */
    private void addLine(ViewGroup list, String label, String extraName) {
        View row = LayoutInflater.from(this).inflate(R.layout.row_review, list, false);
        ((TextView) row.findViewById(R.id.reviewLabel)).setText(label);
        String value = getIntent().getStringExtra(extraName);
        ((TextView) row.findViewById(R.id.reviewValue)).setText(value == null ? "" : value);
        list.addView(row);
    }
}
