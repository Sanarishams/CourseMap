package com.example.coursemap;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class PdfListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        int sem = getIntent().getIntExtra("SEM", 1);
        String category = getIntent().getStringExtra("CATEGORY"); // Category name
        String subject = getIntent().getStringExtra("SUB"); // maybe null

        LinearLayout layout = findViewById(R.id.pdfListLayout);

        // Build base path depending on category/subject
        // Cases:
        // - Student Registration / Handbook / Holidays List -> semX/Student Registration/*.pdf  (direct)
        // - Syllabus/TLEP/Notes/Activity -> semX/<category>/<subject>/*.pdf
        try {
            AssetManager am = getAssets();
            String basePath = null;

            if (category != null && (category.equals("Student Registration") ||
                    category.equals("Handbook") ||
                    category.equals("Holidays List"))) {
                // direct folder listing
                basePath = "sem" + sem + "/" + category;
            } else if (subject != null && category != null) {
                // folder inside category
                basePath = "sem" + sem + "/" + category + "/" + subject;
            } else {
                // fallback — try category folder directly
                basePath = "sem" + sem + "/" + (category != null ? category : "");
            }

            if (basePath == null) return;

            String[] files = am.list(basePath);

            if (files == null || files.length == 0) {
                // nothing found — inform user via a single disabled button
                Button b = new Button(this);
                b.setText("No PDFs found in " + basePath);
                b.setEnabled(false);
                b.setTextColor(Color.DKGRAY);
                layout.addView(b);
                return;
            }

            for (String f : files) {
                // show only pdf files; some folders may include docx — skip them or adapt as needed
                if (f.toLowerCase().endsWith(".pdf")) {
                    Button btn = new Button(this);
                    btn.setText(f.replace(".pdf", ""));
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

                    final String path = basePath + "/" + f;
                    btn.setOnClickListener(v -> {
                        Intent i = new Intent(PdfListActivity.this, PdfViewerActivity.class);
                        i.putExtra("PATH", path);
                        startActivity(i);
                    });
                    btn.setContentDescription("Open PDF " + f);
                    layout.addView(btn);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Button b = new Button(this);
            b.setText("Error loading files");
            b.setEnabled(false);
            layout.addView(b);
        }
    }
}
