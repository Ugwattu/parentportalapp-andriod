package com.vu.parentportalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SendDailyDiary extends AppCompatActivity {

    private AutoCompleteTextView diaryTypeDropdown, classDropdown;
    private EditText diaryDetailInput;
    private MaterialButton saveBtn;
    private MyDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_daily_diary);

        MaterialToolbar toolbar = findViewById(R.id.sendDiary_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });
        diaryTypeDropdown = findViewById(R.id.autoComplete_userType);
        classDropdown = findViewById(R.id.autoComplete_classdiary);
        diaryDetailInput = findViewById(R.id.diaryDetail_input);
        saveBtn = findViewById(R.id.diaryDetailSave_btn);

        dbHelper = new MyDBHelper(this);

        // Adapter for Diary Type
        String[] diaryTypes = {"Classwork", "Homework", "Assignment"};
        ArrayAdapter<String> diaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, diaryTypes);
        diaryTypeDropdown.setAdapter(diaryAdapter);

        // Adapter for Class
        String[] classes = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        classDropdown.setAdapter(classAdapter);


        saveBtn.setOnClickListener(view -> {
            String selectedType = diaryTypeDropdown.getText().toString().trim();
            String selectedClass = classDropdown.getText().toString().trim();
            String detailText = diaryDetailInput.getText().toString().trim();

            if (selectedType.isEmpty() || selectedClass.isEmpty() || detailText.isEmpty()) {
                Toast.makeText(SendDailyDiary.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.saveDiaryEntry(selectedClass, selectedType, detailText);
            if (success) {
                Toast.makeText(SendDailyDiary.this, "Diary entry saved!", Toast.LENGTH_SHORT).show();
                diaryTypeDropdown.setText("");
                classDropdown.setText("");
                diaryDetailInput.setText("");
            } else {
                Toast.makeText(SendDailyDiary.this, "Failed to save diary entry", Toast.LENGTH_SHORT).show();
            }
        });

    }
}