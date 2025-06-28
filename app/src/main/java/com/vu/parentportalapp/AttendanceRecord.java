package com.vu.parentportalapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceRecord extends AppCompatActivity {

    private MyDBHelper myDBHelper;
    private AutoCompleteTextView autoCompleteAttendance;
    private TextView tPresent, tAbsent, tLeave;
    private int studentID;
    private RecyclerView recyclerView;
    private AttendanceRecordAdapter attendanceRecordAdapter;
    private List<AttendanceRecordItem> attendanceRecordList;
    private static final String TAG = "AttendanceRecord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);

        // Initialize views and toolbar
        MaterialToolbar toolbar = findViewById(R.id.attendance_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        myDBHelper = new MyDBHelper(this);

        // Initialize views
        autoCompleteAttendance = findViewById(R.id.autoComplete_attendancerecord);
        tPresent = findViewById(R.id.t_present);
        tAbsent = findViewById(R.id.t_absent);
        tLeave = findViewById(R.id.t_leave);
        recyclerView = findViewById(R.id.attendancerecordRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendanceRecordList = new ArrayList<>();

        attendanceRecordAdapter = new AttendanceRecordAdapter(attendanceRecordList);
        recyclerView.setAdapter(attendanceRecordAdapter);

        // Fetch stored student ID from SharedPreferences
        studentID = getStoredStudentID();

        if (studentID == -1) {
            Log.e(TAG, "Student ID not found in SharedPreferences.");
            return;
        }

        // Fetch distinct month-year combinations for attendance
        List<String> monthsAndYears = myDBHelper.getDistinctMonthsAndYears(this);

        // Populate the dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, monthsAndYears);
        autoCompleteAttendance.setAdapter(adapter);

        // Set item click listener to fetch and display attendance counts for selected month-year
        autoCompleteAttendance.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMonthYear = (String) parent.getItemAtPosition(position);
            fetchAttendanceForMonth(selectedMonthYear);
        });
    }

    private void fetchAttendanceForMonth(String selectedMonthYear) {
        // Convert the selected month-year to yyyy-mm format
        String[] monthYearParts = selectedMonthYear.split(" ");
        String monthYear = monthYearParts[1] + "-" + convertMonthToNumber(monthYearParts[0]);

        // Fetch the attendance data for the selected month-year
        List<AttendanceRecordItem> attendanceList = myDBHelper.getAttendanceForMonth(monthYear, studentID);

        // Update the RecyclerView with the attendance data
        attendanceRecordList.clear();
        attendanceRecordList.addAll(attendanceList);
        attendanceRecordAdapter.notifyDataSetChanged();

        // Fetch and update attendance counts
        int[] counts = myDBHelper.getAttendanceCountsForMonth(monthYear, this);
        tPresent.setText(String.valueOf(counts[0]));
        tAbsent.setText(String.valueOf(counts[1]));
        tLeave.setText(String.valueOf(counts[2]));
    }

    // Fetch the stored student ID from SharedPreferences
    private int getStoredStudentID() {
        SharedPreferences prefs = getSharedPreferences("ParentPortalPrefs", MODE_PRIVATE);
        return prefs.getInt("student_id", -1);
    }

    // Convert month abbreviation (e.g., Jan) to month number (e.g., 01)
    private String convertMonthToNumber(String month) {
        switch (month) {
            case "Jan": return "01";
            case "Feb": return "02";
            case "Mar": return "03";
            case "Apr": return "04";
            case "May": return "05";
            case "Jun": return "06";
            case "Jul": return "07";
            case "Aug": return "08";
            case "Sep": return "09";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default: return "01";
        }
    }
}
