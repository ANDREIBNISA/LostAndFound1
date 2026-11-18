package com.example.lostandfound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;

/**
 * Reports screen.
 *
 * Portrait: a "Make a report" button, the current reports, and a "Previous reports" section.
 * Each report is a copy of the XML design item_report.xml. The text below is placeholder
 * data; when the database arrives, the rows will come from it instead.
 *
 * Landscape: the table from the worksheet, written out in the XML. Only the "tick all" box needs code.
 */
public class ReportStatusActivity extends BaseActivity {

    // { item name, report id, date, status }
    private static final String[][] CURRENT_REPORTS = {
            {"Black leather wallet", "L080910", "September 11, 2026", "Not Found"},
            {"ryzen 9 9980x3d", "L067676", "September 11, 2026", "Not Found"},
            {"128gb ddr5 ram", "L694201", "September 11, 2026", "Not Found"},
            {"iPair 67 pro max 64tb", "F000002", "September 11, 2026", "Found"},
    };

    private static final String[][] PREVIOUS_REPORTS = {
            {"1,000$ worth of cash", "F123456", "September 4, 2026", "Returned"},
            {"Black leather wallet", "F193847", "August 29, 2026", "Returned"},
            {"rtx4090", "L000067", "August 20, 2026", "Closed"},
    };

    private ViewGroup currentList;

    @Override
    protected int currentNavId() {
        return R.id.navStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_status);
        setUpNavigation();

        currentList = findViewById(R.id.currentReportsList);
        if (currentList != null) {
            setUpPortraitReports();
        } else {
            setUpLandscapeTable();
        }
    }

    // ---------- Portrait ----------

    private void setUpPortraitReports() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup previousList = findViewById(R.id.previousReportsList);

        for (String[] report : CURRENT_REPORTS) {
            currentList.addView(makeReportCard(inflater, currentList, report, true));
        }
        for (String[] report : PREVIOUS_REPORTS) {
            previousList.addView(makeReportCard(inflater, previousList, report, false));
        }

        onClickOpen(R.id.makeReportButton, R.id.navReport);
        onClickOpen(R.id.createReportButton, R.id.navReport);

        // "Previous reports" starts closed; tapping the row opens and closes it.
        View chevron = findViewById(R.id.previousChevron);
        findViewById(R.id.previousHeader).setOnClickListener(v -> {
            boolean opening = previousList.getVisibility() != View.VISIBLE;
            previousList.setVisibility(opening ? View.VISIBLE : View.GONE);
            chevron.setRotation(opening ? 180f : 0f);
        });

        showEmptyMessageIfNoReports();
    }

    /** One copy of item_report.xml filled with one report's text. */
    private View makeReportCard(LayoutInflater inflater, ViewGroup parent, String[] report, boolean hasMenu) {
        View card = inflater.inflate(R.layout.item_report, parent, false);
        ((TextView) card.findViewById(R.id.reportName)).setText(report[0]);
        ((TextView) card.findViewById(R.id.reportMeta)).setText(report[1] + "  •  " + report[2]);
        showStatus(card.findViewById(R.id.reportStatus), report[3]);
        // Previous reports are finished, so they have no options menu.
        card.findViewById(R.id.reportMenu).setVisibility(hasMenu ? View.VISIBLE : View.GONE);
        return card;
    }

    private void showStatus(View statusView, String status) {
        TextView text = (TextView) statusView;
        text.setText(status);
        boolean good = status.equals("Found") || status.equals("Returned");
        text.setTextColor(getColor(good ? R.color.fi_found_green : R.color.white));
    }

    /** Called by the three dots on a report (android:onClick in item_report.xml). */
    public void onReportMenuClick(View dots) {
        // Walk up from the dots to the card that sits directly inside the current reports list.
        View card = dots;
        while (card.getParent() != currentList) {
            card = (View) card.getParent();
        }
        final View chosenCard = card;

        PopupMenu popup = new PopupMenu(this, dots);
        popup.getMenuInflater().inflate(R.menu.report_row_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.reportMarkFound) {
                showStatus(chosenCard.findViewById(R.id.reportStatus), "Found");
                toast("Marked as found (prototype: nothing is saved)");
            } else if (id == R.id.reportEdit) {
                toast("Editing opens the report form (prototype: it starts empty)");
                openScreen(R.id.navReport);
            } else if (id == R.id.reportDelete) {
                currentList.removeView(chosenCard);
                toast("Report deleted (prototype: it comes back when you reopen the screen)");
                showEmptyMessageIfNoReports();
            }
            return true;
        });
        popup.show();
    }

    /** With no current reports, the empty message and "Create a report" button replace the list. */
    private void showEmptyMessageIfNoReports() {
        boolean empty = currentList.getChildCount() == 0;
        findViewById(R.id.reportsEmpty).setVisibility(empty ? View.VISIBLE : View.GONE);
        findViewById(R.id.currentReportsGroup).setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    // ---------- Landscape ----------

    private void setUpLandscapeTable() {
        ViewGroup table = findViewById(R.id.reportTable);
        CheckBox selectAll = findViewById(R.id.selectAllCheck);
        if (table == null || selectAll == null) {
            return;
        }

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
