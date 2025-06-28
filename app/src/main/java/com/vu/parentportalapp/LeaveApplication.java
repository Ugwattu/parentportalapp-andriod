package com.vu.parentportalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaveApplication extends AppCompatActivity {
    Button fromButton, toButton, submitButton;
    MaterialAutoCompleteTextView leaveTypeDropdown;
    EditText reasonEditText;
    DatePickerDialog fromDatePickerDialog, toDatePickerDialog;

    String selectedFromDate = "", selectedToDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        // Initialize buttons
        fromButton = findViewById(R.id.leave_fromDatePicker);
        toButton = findViewById(R.id.leave_toDatePicker);
        submitButton = findViewById(R.id.leave_submit_btn);
        leaveTypeDropdown = findViewById(R.id.autoComplete_leavedropdown);
        reasonEditText = findViewById(R.id.leave_reason);

        String[] leaveTypes = {"Sick Leave", "Casual Leave", "Personal Leave"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, leaveTypes);
        leaveTypeDropdown.setAdapter(adapter);

        //      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.leave_application_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());


        // Set onClick listeners for date pickers
        fromButton.setOnClickListener(v -> openDatePicker(fromDatePickerDialog)); // Opens fromDatePickerDialog
        toButton.setOnClickListener(v -> openDatePicker(toDatePickerDialog)); // Opens toDatePickerDialog
        submitButton.setOnClickListener(v -> submitLeaveApplication());

        // Set MaterialDatePicker for from date
        fromButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> fromPicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Start Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            fromPicker.addOnPositiveButtonClickListener(selection -> {
                selectedFromDate = formatDate(selection);
                fromButton.setText(selectedFromDate);
            });

            fromPicker.show(getSupportFragmentManager(), "FROM_DATE_PICKER");
        });

        // Set MaterialDatePicker for to date
        toButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> toPicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select End Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            toPicker.addOnPositiveButtonClickListener(selection -> {
                selectedToDate = formatDate(selection);
                toButton.setText(selectedToDate);
            });

            toPicker.show(getSupportFragmentManager(), "TO_DATE_PICKER");
        });

    }

    private void submitLeaveApplication() {
        String startDate = fromButton.getText().toString();
        String endDate = toButton.getText().toString();
        String leaveType = leaveTypeDropdown.getText().toString().trim();
        String reason = reasonEditText.getText().toString();

        // Get stored student ID
        SharedPreferences prefs = getSharedPreferences("ParentPortalPrefs", Context.MODE_PRIVATE);
        int studentID = prefs.getInt("student_id", -1);

        if (studentID == -1) {
            Toast.makeText(this, "Student ID not found. Please login again.", Toast.LENGTH_LONG).show();
            return;
        }

        if (startDate.isEmpty() || endDate.isEmpty() || reason.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDBHelper dbHelper = new MyDBHelper(this);
        long result = dbHelper.insertLeaveApplication(studentID, startDate, endDate, leaveType, reason, "Pending");

        if (result != -1) {
            Toast.makeText(this, "Leave application submitted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Optionally go back
        } else {
            Toast.makeText(this, "Failed to submit leave application", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatDate(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(dateInMillis));
    }

    // Method to show the correct DatePickerDialog
    public void openDatePicker(DatePickerDialog datePickerDialog) {
        datePickerDialog.show();
    }
}
