package com.example.coursemap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText usnInput;
    private Button loginBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.appTitle);

        // Apply a beautiful gradient to the title text
        Shader textShader = new LinearGradient(
                0, 0, 0, title.getTextSize(),
                new int[]{
                        Color.parseColor("#5A72A0"), // deep blue
                        Color.parseColor("#A0C4FF"), // soft pastel blue
                        Color.parseColor("#CDE8FF")  // very light blue
                },
                null,
                Shader.TileMode.CLAMP);
        title.getPaint().setShader(textShader);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        usnInput = findViewById(R.id.usnInput);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {
            String usn = usnInput.getText().toString().trim();

            if (usn.isEmpty()) {
                Toast.makeText(this, "Please enter USN", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("Students").document(usn).get()
                    .addOnSuccessListener(doc -> {
                        if (doc.exists()) {
                            Toast.makeText(this, "Welcome back, " + usn, Toast.LENGTH_SHORT).show();
                            goToSemesterPage(usn);
                        } else {
                            db.collection("Students").document(usn)
                                    .set(new Student(usn))
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "New USN registered: " + usn, Toast.LENGTH_SHORT).show();
                                        goToSemesterPage(usn);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Error registering USN", Toast.LENGTH_SHORT).show();
                                        Log.e("FS_ERROR", "Error registering USN", e);
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error accessing database", Toast.LENGTH_SHORT).show();
                        Log.e("FS_ERROR", "Firestore access failed", e);
                    });
        });
    }

    private void goToSemesterPage(String usn) {
        Intent intent = new Intent(MainActivity.this, SemesterActivity.class);
        intent.putExtra("USN", usn);
        startActivity(intent);
        finish();
    }

    static class Student {
        public String usn;
        public Student() {}
        public Student(String usn) { this.usn = usn; }
    }
}
