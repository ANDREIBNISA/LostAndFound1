package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/** Screen 04: Report status. The nine rows are written in the XML; this only handles the "tick all" box. */
public class ReportStatusActivity extends BaseActivity {

    @Override
    protected int currentNavId() {
        return R.id.navStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_status);
        setUpNavigation();

        ViewGroup table = findViewById(R.id.reportTable);
        CheckBox selectAll = findViewById(R.id.selectAllCheck);

        // The box in the header ticks or un-ticks the box at the end of every row.
        selectAll.setOnCheckedChangeListener((button, checked) -> {
            for (int i = 0; i < table.getChildCount(); i++) {
                ViewGroup row = (ViewGroup) table.getChildAt(i);
                View lastCell = row.getChildAt(row.getChildCount() - 1);
                if (lastCell instanceof CheckBox) {
                    ((CheckBox) lastCell).setChecked(checked);
                }
            }
        });
    }
}
