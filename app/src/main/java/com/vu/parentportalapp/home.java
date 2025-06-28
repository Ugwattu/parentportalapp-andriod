package com.vu.parentportalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
//add binding so ui elements changes automatically
    Button timeTableButton, leaveApplicationButton, reportCardButton,
        dailyDiaryButton, attendanceRecordButton, feeDetailButton, transportFacilityButton,
        syllabusMainButton, feedBackButton;
    CardView nameClass;
    MaterialToolbar topAppBar;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

// Toolbar setup
        topAppBar = findViewById(R.id.home_toolbar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(v -> {
            // Handle navigation icon press
            finish();
        });

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
if (itemId == R.id.logout_btn_icon) {
                    Intent logoutIntent = new Intent(home.this, com.vu.parentportalapp.MainActivity.class);
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Clears activity stack
                    startActivity(logoutIntent);
                    finish(); // Close the current activity
                    return true;
                } else {
                    return false;
                }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                return true; // Already in home
            } else if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(this, Studentprofile.class));
            } else if (item.getItemId() == R.id.nav_announcemnet) {
                startActivity(new Intent(this, Announcement.class));
            }

            overridePendingTransition(0, 0);
            return true;
        });


        nameClass = findViewById(R.id.name_class);
        timeTableButton = findViewById(R.id.timetable_btn);
        leaveApplicationButton = findViewById(R.id.application_btn);
        reportCardButton = findViewById(R.id.reportcard_btn);
        dailyDiaryButton = findViewById(R.id.dailydiary_btn);
        attendanceRecordButton = findViewById(R.id.attendance_btn);
        feeDetailButton = findViewById(R.id.feedetail_btn);
        transportFacilityButton = findViewById(R.id.transport_btn);
        syllabusMainButton = findViewById(R.id.syllabus_main_btn);
        feedBackButton = findViewById(R.id.feedback_btn);


        nameClass.setOnClickListener(v -> startActivity(new Intent(home.this, Studentprofile.class)));
        timeTableButton.setOnClickListener(v -> startActivity(new Intent(home.this, TimeTable.class)));
        leaveApplicationButton.setOnClickListener(v -> startActivity(new Intent(home.this, LeaveApplication.class)));
        reportCardButton.setOnClickListener(v -> startActivity(new Intent(home.this, ReportCard.class)));
        dailyDiaryButton.setOnClickListener(v -> startActivity(new Intent(home.this, DailyDiary.class)));
        attendanceRecordButton.setOnClickListener(v -> startActivity(new Intent(home.this, AttendanceRecord.class)));
        feeDetailButton.setOnClickListener(v -> startActivity(new Intent(home.this, FeeDetail.class)));
        transportFacilityButton.setOnClickListener(v -> startActivity(new Intent(home.this, TransportFacility.class)));
        syllabusMainButton.setOnClickListener(v -> startActivity(new Intent(home.this, syllabus_main.class)));
        feedBackButton.setOnClickListener(v -> startActivity(new Intent(home.this, Feedback.class)));


        // Fetch student details
        MyDBHelper dbHelper = new MyDBHelper(this);
        String[] studentData = dbHelper.getStudentDetails(home.this);

        TextView studentNameTextView = findViewById(R.id.home_Stname);
        TextView studentClassTextView = findViewById(R.id.home_Stclass);

        studentNameTextView.setText(studentData[0]); // Set Student Name
        studentClassTextView.setText(studentData[1]); // Set Student Class

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu from the XML file
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;  // Return true to display the menu
    }
}