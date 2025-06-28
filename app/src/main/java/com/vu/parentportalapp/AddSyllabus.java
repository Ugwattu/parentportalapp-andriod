package com.vu.parentportalapp;

import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class AddSyllabus extends AppCompatActivity {

    MaterialButton saveBtn;
    AutoCompleteTextView ClassDropdown;
    EditText StudentClassInput, SyllabusDetailInput;
    MyDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_syllabus);

        MaterialToolbar toolbar = findViewById(R.id.sendSyllabus_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        ClassDropdown = findViewById(R.id.autoComplete_ClassType);
        StudentClassInput = findViewById(R.id.enterStudentClass_input);
        SyllabusDetailInput = findViewById(R.id.syllabusDetail_input);
        saveBtn = findViewById(R.id.syllabusDetailSave_btn);

        dbHelper = new MyDBHelper(this);

        String[] classes = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        ClassDropdown.setAdapter(classAdapter);

        saveBtn.setOnClickListener(v -> {
            String studentClass = ClassDropdown.getText().toString().trim();
            String subject = StudentClassInput.getText().toString().trim();
            String topic = SyllabusDetailInput.getText().toString().trim();

            if (studentClass.isEmpty() || subject.isEmpty() || topic.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertSyllabus(studentClass, subject, topic);

            if (isInserted) {
                Toast.makeText(this, "Syllabus saved successfully!", Toast.LENGTH_SHORT).show();
                ClassDropdown.setText("");
                StudentClassInput.setText("");
                SyllabusDetailInput.setText("");
            } else {
                Toast.makeText(this, "Failed to save syllabus", Toast.LENGTH_SHORT).show();
            }
        });


    }
}