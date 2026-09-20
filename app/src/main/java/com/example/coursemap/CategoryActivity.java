package com.example.coursemap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CategoryActivity extends AppCompatActivity {

    private static final String[] CATEGORIES = {
            "Student Registration",
            "Handbook",
            "Syllabus",
            "TLEP",
            "Notes",
            "Activity",
            "Holidays List"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        int sem = getIntent().getIntExtra("SEM", 1);
        LinearLayout layout = findViewById(R.id.categoryLayout);

        for (String cat : CATEGORIES) {
            Button btn = new Button(this);
            btn.setText(cat);
            btn.setBackgroundResource(R.drawable.button_ripple);
            btn.setTextColor(Color.WHITE);
            btn.setAllCaps(false);
            btn.setTextSize(16);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            btn.setLayoutParams(params);

            btn.setOnClickListener(v -> {
                // Categories that go directly to PDFs: Student Registration, Handbook, Holidays List
                if (cat.equals("Student Registration") || cat.equals("Handbook") || cat.equals("Holidays List")) {
                    Intent i = new Intent(CategoryActivity.this, PdfListActivity.class);
                    i.putExtra("SEM", sem);
                    i.putExtra("CATEGORY", cat); // PdfListActivity will handle direct-folder listing
                    startActivity(i);
                } else {
                    // Syllabus / TLEP / Notes / Activity -> choose subject first
                    Intent i = new Intent(CategoryActivity.this, SubjectActivity.class);
                    i.putExtra("SEM", sem);
                    i.putExtra("CATEGORY", cat); // SubjectActivity will load subjects for this category
                    startActivity(i);
                }
            });

            btn.setContentDescription(cat + " for semester " + sem);
            layout.addView(btn);
        }
    }
}
