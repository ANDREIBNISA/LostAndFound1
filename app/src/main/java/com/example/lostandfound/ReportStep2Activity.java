package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Making a report, step 2 of 3 (more details): description, category, date and a photo.
 * It keeps everything step 1 sent and adds its own answers for step 3.
 */
public class ReportStep2Activity extends BaseActivity {

    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_PHOTO = "photo";

    private boolean photoAdded = false;

    @Override
    protected int currentNavId() {
        return R.id.navReport;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_step2);
        setUpNavigation();

        // There is no photo picker yet: tapping the box only pretends to add a photo.
        TextView photoText = findViewById(R.id.photoText);
        findViewById(R.id.photoBox).setOnClickListener(v -> {
            photoAdded = !photoAdded;
            photoText.setText(photoAdded ? "Photo added (placeholder). Tap to remove" : "Tap to add a photo");
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.nextStepButton).setOnClickListener(v -> {
            hideKeyboard();
            goToStep3();
        });
    }

    private void goToStep3() {
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        EditText dateInput = findViewById(R.id.dateInput);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        String description = descriptionInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        if (description.isEmpty() || date.isEmpty()) {
            toast("Please fill in the description and the date");
            return;
        }

        Intent next = new Intent(this, ReportStep3Activity.class);
        next.putExtras(getIntent());   // everything from step 1
        next.putExtra(EXTRA_DESCRIPTION, description);
        next.putExtra(EXTRA_CATEGORY, categorySpinner.getSelectedItem().toString());
        next.putExtra(EXTRA_DATE, date);
        next.putExtra(EXTRA_PHOTO, photoAdded ? "Added (placeholder)" : "None");
        startActivity(next);
    }
}
