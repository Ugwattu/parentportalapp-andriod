package com.vu.parentportalapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarkAttendance extends AppCompatActivity {
    MaterialButton saveBtn;
    AutoCompleteTextView classDropdown;
    RecyclerView recyclerView;
    MyDBHelper dbHelper;
    MarkAttendanceAdapter adapter;
    List<MarkAttendanceItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        MaterialToolbar toolbar = findViewById(R.id.markAttendance_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());


        // Initialize components
        saveBtn = findViewById(R.id.attendanceSave_btn);
        classDropdown = findViewById(R.id.autoComplete_AttendanceClassType);
        recyclerView = findViewById(R.id.markAttendanceRecycler);

        dbHelper = new MyDBHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the items list (make sure it is not null)
        items = new ArrayList<>();  // Ensure you initialize the items list
        Log.d("MarkAttendance", "Items size onCreate: " + items.size()); // Debug log
        adapter = new MarkAttendanceAdapter(items);
        recyclerView.setAdapter(adapter);

        // Populate the class dropdown
        populateClassDropdown();

        classDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedClass = (String) parent.getItemAtPosition(position);
            loadStudentsForClass(selectedClass);
        });

        // Handle save button click
        saveBtn.setOnClickListener(v -> saveAttendance());
    }

    private void populateClassDropdown() {
        // Fetch the available classes from the database (assuming your DB has a class list)
        List<String> classes = dbHelper.getAllClasses(); // Method to get all class names

        Log.d("MarkAttendance", "Classes: " + classes.toString());

        // Create an ArrayAdapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        classDropdown.setAdapter(classAdapter);
    }


    private void loadStudentsForClass(String className) {
        Log.d("MarkAttendance", "Loading students for class: " + className);
        items.clear();

        List<String> students = dbHelper.getStudentsByClass(className);
        Log.d("MarkAttendance", "Fetched students: " + students);
        if (students.isEmpty()) {
            Toast.makeText(this, "No students found for selected class", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            return;
        }

        for (int i = 0; i < students.size(); i += 2) {
            String name = students.get(i);
            String rollNo = students.get(i + 1);

            items.add(new MarkAttendanceItem(rollNo, name, "Absent")); // default = Absent
        }
        Log.d("MarkAttendance", "Total students added: " + items.size());
        adapter.notifyDataSetChanged();
    }

    private void saveAttendance() {
        String className = classDropdown.getText().toString();
        List<String> students = dbHelper.getStudentsByClass(className);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        for (int i = 0; i < students.size(); i += 2) {
            String studentName = students.get(i);
            String studentRollno = students.get(i + 1);
            View itemView = recyclerView.getLayoutManager().findViewByPosition(i / 2);

            if (itemView != null) {
                MaterialSwitch attendanceSwitch = itemView.findViewById(R.id.attendanceStatus_switch);
                String status = attendanceSwitch.isChecked() ? "Present" : "Absent";
                int studentId = dbHelper.getStudentIdByRollNo(studentRollno);

                // ✅ Check for existing attendance before inserting
                if (!dbHelper.isAttendanceAlreadyMarked(studentId, currentDate)) {
                    dbHelper.saveAttendanceData(studentId, currentDate, status);
                } else {
                    Log.d("Attendance", "Skipped: Already marked for Student_ID " + studentId + " on " + currentDate);
                }
            }
        }

        Toast.makeText(this, "Attendance saved successfully", Toast.LENGTH_SHORT).show();
    }
}