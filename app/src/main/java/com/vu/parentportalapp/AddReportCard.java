package com.vu.parentportalapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddReportCard extends AppCompatActivity {

    MaterialButton saveBtn;
    AutoCompleteTextView classDropdown;
    EditText subjectName, termName;
    RecyclerView recyclerView;
    MyDBHelper dbHelper;
    AddReportCardAdapter adapter;
    List<AddReportCardItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report_card);

        MaterialToolbar toolbar = findViewById(R.id.addReportCard_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        saveBtn = findViewById(R.id.reportCardSave_btn);
        classDropdown = findViewById(R.id.autoComplete_ReportCardClassType);
        recyclerView = findViewById(R.id.addReportCardRecycler);
        subjectName = findViewById(R.id.subjectName_input);
        termName = findViewById(R.id.termName_input);

        dbHelper = new MyDBHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        Log.d("AddReportcard", "Items size onCreate: " + items.size()); // Debug log
        adapter = new AddReportCardAdapter(items);
        recyclerView.setAdapter(adapter);

        populateClassDropdown();

        classDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedClass = (String) parent.getItemAtPosition(position);
            loadStudentsForClass(selectedClass);
        });

        // Handle save button click
        saveBtn.setOnClickListener(v -> insertReportCard());
    }

    private void populateClassDropdown() {
        // Fetch the available classes from the database (assuming your DB has a class list)
        List<String> classes = dbHelper.getAllClasses(); // Method to get all class names

        Log.d("AddReportCard", "Classes: " + classes.toString());

        // Create an ArrayAdapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        classDropdown.setAdapter(classAdapter);
    }


    private void loadStudentsForClass(String className) {
        Log.d("AddReportCard", "Loading students for class: " + className);
        items.clear();

        List<String> students = dbHelper.getStudentsByClass(className);
        Log.d("AddReportCard", "Fetched students: " + students);
        if (students.isEmpty()) {
            Toast.makeText(this, "No students found for selected class", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            return;
        }

        for (int i = 0; i < students.size(); i += 2) {
            String name = students.get(i);
            String rollNo = students.get(i + 1);

            items.add(new AddReportCardItem(rollNo, name, " ")); // default = Absent
        }
        Log.d("AddReportCard", "Total students added: " + items.size());
        adapter.notifyDataSetChanged();
    }
    private void insertReportCard() {
        MyDBHelper dbHelper = new MyDBHelper(this);

        EditText termInput = findViewById(R.id.termName_input);
        String term = termInput.getText().toString().trim();
        String subject = subjectName.getText().toString().trim();

        if (term.isEmpty() || subject.isEmpty()) {
            Toast.makeText(this, "Please enter both Term and Subject name", Toast.LENGTH_SHORT).show();
            return;
        }

        List<AddReportCardItem> items = adapter.getItems();

        for (AddReportCardItem item : items) {
            String rollNo = item.getRollNo();
            int studentId = dbHelper.getStudentIdByRollNo(rollNo);
            String marksStr = item.getAddSubjectNumber().trim();

            if (studentId == -1) {
                Log.e("InsertReportCard", "Invalid student ID for roll no: " + rollNo);
                continue;
            }

            if (marksStr.isEmpty()) {
                Log.e("InsertReportCard", "Marks empty for: " + rollNo);
                continue;
            }

            try {
                int marksObtained = Integer.parseInt(marksStr);
                JSONObject newSubjectEntry = new JSONObject();
                newSubjectEntry.put("MarksObtained", marksObtained);
                newSubjectEntry.put("MaxMarks", 100);

                JSONObject updatedMarks;

                if (dbHelper.checkReportCardExists(studentId, term)) {
                    // Fetch existing JSON
                    String existingJson = dbHelper.getReportCardMarksJson(studentId, term); // YOU NEED TO IMPLEMENT THIS
                    updatedMarks = existingJson != null ? new JSONObject(existingJson) : new JSONObject();
                } else {
                    updatedMarks = new JSONObject();
                }

                // Add/Update subject
                updatedMarks.put(subject, newSubjectEntry);

                boolean success;
                if (dbHelper.checkReportCardExists(studentId, term)) {
                    success = dbHelper.updateReportCard(studentId, term, updatedMarks.toString());
                } else {
                    success = dbHelper.insertReportCard(studentId, term, updatedMarks.toString());
                }

                if (!success) {
                    Log.e("InsertReportCard", "Failed to save for studentId: " + studentId);
                }

            } catch (JSONException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this, "Report card saved/updated successfully", Toast.LENGTH_SHORT).show();
    }



}