package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpdateFeeDetail extends AppCompatActivity {
    private AutoCompleteTextView classDropdown, studentDropdown, feeTypeDropdown;
    private EditText amountInput, dueDateInput;
    private Button submitButton;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fee_detail);

        MaterialToolbar toolbar = findViewById(R.id.updatefeedetail_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        dbHelper = new MyDBHelper(this);

        // Fetching AutoCompleteTextView correctly from TextInputLayout
        TextInputLayout classLayout = findViewById(R.id.selectStudentClass);
        TextInputLayout studentLayout = findViewById(R.id.selectStudent);
        classDropdown = (AutoCompleteTextView) classLayout.getEditText();
        studentDropdown = (AutoCompleteTextView) studentLayout.getEditText();

        TextInputLayout feeTypeLayout = findViewById(R.id.selectStudentFeeType);
        feeTypeDropdown = (AutoCompleteTextView) feeTypeLayout.getEditText();

        amountInput = findViewById(R.id.selectstudentfeeamount_input);
        dueDateInput = findViewById(R.id.studentfeeduedate_input);
        submitButton = findViewById(R.id.updateFeeDetailSave_btn);

        dueDateInput.setFocusable(false);
        dueDateInput.setClickable(true);

        setupClassDropdown();
        setupFeeTypeDropdown();
        setupDueDatePicker();

        submitButton.setOnClickListener(v -> submitFeeDetails());
    }

    private void setupClassDropdown() {
        List<String> classes = dbHelper.getAllClasses();

        if (classes == null || classes.isEmpty()) {
            Toast.makeText(this, "No classes found", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        classDropdown.setAdapter(classAdapter);

        // When a class is selected, update the student dropdown
        classDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedClass = parent.getItemAtPosition(position).toString();
            setupStudentDropdown(selectedClass);
        });
    }

    private void setupStudentDropdown(String className) {
        List<String> rawStudents = dbHelper.getStudentsByClass(className);  // This should return [name1, roll1, name2, roll2, ...]

        if (rawStudents == null || rawStudents.isEmpty()) {
            Toast.makeText(this, "No students found for selected class", Toast.LENGTH_SHORT).show();
            studentDropdown.setAdapter(null);
            return;
        }

        // Format: "123. Ali Khan"
        List<String> formattedList = new ArrayList<>();
        for (int i = 0; i < rawStudents.size(); i += 2) {
            String name = rawStudents.get(i);
            String roll = rawStudents.get(i + 1);
            formattedList.add(roll + ". " + name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, formattedList);
        studentDropdown.setAdapter(adapter);
        studentDropdown.setThreshold(1);
    }

    private void setupFeeTypeDropdown() {
        String[] feeTypes = {"Tuition", "Hostel", "Transport"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, feeTypes);
        feeTypeDropdown.setAdapter(adapter);
    }

    private void setupDueDatePicker() {
        dueDateInput.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Due Date")
                    .build();

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                dueDateInput.setText(sdf.format(calendar.getTime()));
            });
        });
    }

    private void submitFeeDetails() {
        String studentText = studentDropdown.getText().toString().trim();
        String feeType = feeTypeDropdown.getText().toString().trim();
        String amount = amountInput.getText().toString().trim();
        String dueDate = dueDateInput.getText().toString().trim();

        if (studentText.isEmpty() || feeType.isEmpty() || amount.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extract student name and roll number
        String[] studentParts = studentText.split("\\. ");
        String rollNo = studentParts[0];
        String studentName = studentParts[1];

        // Get the student ID based on name and class
        String className = classDropdown.getText().toString().trim();
        int studentId = dbHelper.getStudentId(studentName, className);

        if (studentId == -1) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.insertFeeDetail(studentId, feeType, Double.parseDouble(amount), dueDate);
        if (result != -1) {
            Toast.makeText(this, "Fee entry added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding fee entry", Toast.LENGTH_SHORT).show();
        }
    }
}
