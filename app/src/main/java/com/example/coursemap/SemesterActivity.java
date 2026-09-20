package com.example.coursemap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SemesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        LinearLayout layout = findViewById(R.id.semesterLayout);

        // Create 6 semester buttons
        for (int i = 1; i <= 6; i++) {
            Button btn = new Button(this);
            btn.setText("Semester " + i);
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

            final int sem = i;
            btn.setOnClickListener(v -> {
                Intent intent = new Intent(SemesterActivity.this, CategoryActivity.class);
                intent.putExtra("SEM", sem);
                startActivity(intent);
            });

            btn.setContentDescription("Open semester " + i);
            layout.addView(btn);
        }
    }
}
