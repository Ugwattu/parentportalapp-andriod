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

public class AddTimetable extends AppCompatActivity {

    private AutoCompleteTextView classDropdown, dayDropdown, durationDropdown;
    private EditText SubjectInput, TeacherInput;
    private MaterialButton saveBtn;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_timetable);

        MaterialToolbar toolbar = findViewById(R.id.addTimetable_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        classDropdown = findViewById(R.id.autoComplete_timeTableClass);
        dayDropdown = findViewById(R.id.autoComplete_timeTableDay);
        durationDropdown = findViewById(R.id.autoComplete_timeTableDuration);
        SubjectInput = findViewById(R.id.timeTableSubject_input);
        TeacherInput = findViewById(R.id.timeTableTeacher_input);
        saveBtn = findViewById(R.id.timeTableSave_btn);

        dbHelper = new MyDBHelper(this);

        String[] classes = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        classDropdown.setAdapter(classAdapter);

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, days);
        dayDropdown.setAdapter(dayAdapter);

        String[] duration = {"08:30 AM - 09:00 AM", "09:00 AM - 09:30 AM", "09:30 AM - 10:00 AM", "10:00 AM - 10:30 AM", "10:30 AM - 11:00 AM", "11:00 AM - 11:30 AM", "11:30 AM - 12:00 PM", "12:00 PM - 12:30 PM", "12:30 PM - 01:00 PM", "01:00 PM - 01:30 PM", "01:30 PM - 02:00 PM"};
        ArrayAdapter<String> durationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, duration);
        durationDropdown.setAdapter(durationAdapter);

        HashMap<String, String> dayShortMap = new HashMap<>();
        dayShortMap.put("Monday", "Mon");
        dayShortMap.put("Tuesday", "Tue");
        dayShortMap.put("Wednesday", "Wed");
        dayShortMap.put("Thursday", "Thu");
        dayShortMap.put("Friday", "Fri");
        dayShortMap.put("Saturday", "Sat");


        saveBtn.setOnClickListener(v -> {
            String selectedClass = classDropdown.getText().toString().trim();
            String fullDay = dayDropdown.getText().toString().trim();
            String selectedDay = dayShortMap.containsKey(fullDay) ? dayShortMap.get(fullDay) : fullDay;
            String selectedDuration = durationDropdown.getText().toString().trim();
            String subject = SubjectInput.getText().toString().trim();
            String teacher = TeacherInput.getText().toString().trim();

            if (selectedClass.isEmpty() || selectedDay.isEmpty() || selectedDuration.isEmpty() ||
                    subject.isEmpty() || teacher.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertTimeTable(selectedClass, selectedDay, selectedDuration, subject, teacher);
            if (success) {
                Toast.makeText(this, "Timetable saved successfully", Toast.LENGTH_SHORT).show();
                // Optional: Clear fields
                classDropdown.setText("");
                dayDropdown.setText("");
                durationDropdown.setText("");
                SubjectInput.setText("");
                TeacherInput.setText("");
            } else {
                Toast.makeText(this, "Failed to save timetable", Toast.LENGTH_SHORT).show();
            }
        });
    }
}