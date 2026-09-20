package com.example.coursemap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SubjectActivity extends AppCompatActivity {

    // IMPORTANT: These subject arrays must match your assets folder names exactly (case & spacing)
    // Update these arrays to match each semester subjects in your assets (semX/Notes/... etc)
    private static final String[][] SEM_SUBJECTS = {
            // sem1
            {"C Programming", "Computer Organisation", "English", "FCA", "FOM", "Indian Knowledge"},
            // sem2
            {"Data Structures", "Java", "Digital Design", "Operating System", "Indian Constitution"},
            // sem3
            {"Software Engineering", "DBMS", "Computer Networks", "Web Technologies", "Quantitative Aptitude"},
            // sem4
            {"Cloud Technology", "Employability Skills", "Python Programming", "Network Admistration", "Indian Constitution"},
            // sem5
            {"AAD", "MDC", "Research Methodology", "Storage Management", "Technical Writing"},
            // sem6
            {"Research Methodology ", "WAWS", "Amazon Web Services", "IOS Application Development"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        int sem = getIntent().getIntExtra("SEM", 1);
        String category = getIntent().getStringExtra("CATEGORY"); // Syllabus / Notes / Activity / TLEP

        LinearLayout layout = findViewById(R.id.subjectLayout);

        String[] subjects;
        if (sem >= 1 && sem <= SEM_SUBJECTS.length) subjects = SEM_SUBJECTS[sem - 1];
        else subjects = new String[] {"Subject 1"};

        for (String sub : subjects) {
            Button btn = new Button(this);
            btn.setText(sub);
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
                Intent i = new Intent(SubjectActivity.this, PdfListActivity.class);
                i.putExtra("SEM", sem);
                i.putExtra("CATEGORY", category); // Syllabus/Notes/Activity/TLEP
                i.putExtra("SUB", sub);          // subject folder
                startActivity(i);
            });

            btn.setContentDescription("Open " + sub + " " + category);
            layout.addView(btn);
        }
    }
}
