package com.vu.parentportalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class adminhome extends AppCompatActivity {

    Button RegisterStudent, UploadfeeVoucher, SendNotification, SendDiary, AddSyllabus, addTimeTable,
            addTransport, checkLeave, markAttendance, addReportCard, sendFeedback;
    MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        topAppBar = findViewById(R.id.adminhome_toolbar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(v -> {
            // Handle navigation icon press
            finish();
        });

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.logout_btn_icon) {
                Intent logoutIntent = new Intent(adminhome.this, MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                finish();
                return true;
            }

            return false;
        });

        RegisterStudent = findViewById(R.id.registerStudent_btn);
        UploadfeeVoucher = findViewById(R.id.feeVoucher_btn);
        SendNotification = findViewById(R.id.sendNotification_btn);
        SendDiary = findViewById(R.id.sendDiary_btn);
        AddSyllabus = findViewById(R.id.addSyllabus_btn);
        addTimeTable = findViewById(R.id.addTimeTable_btn);
        addTransport = findViewById(R.id.addTransportDetail_btn);
        checkLeave = findViewById(R.id.checkLeave_btn);
        markAttendance = findViewById(R.id.addAttendance_btn);
        addReportCard = findViewById(R.id.addReportCard_btn);
        sendFeedback = findViewById(R.id.sendFeedback_btn);

        RegisterStudent.setOnClickListener(v -> startActivity(new Intent(adminhome.this, RegisterStudent.class)));
        UploadfeeVoucher.setOnClickListener(v -> startActivity(new Intent(adminhome.this, UpdateFeeDetail.class)));
        SendNotification.setOnClickListener(v -> startActivity(new Intent(adminhome.this, SendNotification.class)));
        SendDiary.setOnClickListener(v -> startActivity(new Intent(adminhome.this, SendDailyDiary.class)));
        AddSyllabus.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AddSyllabus.class)));
        addTimeTable.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AddTimetable.class)));
        addTransport.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AddTransport.class)));
        checkLeave.setOnClickListener(v -> startActivity(new Intent(adminhome.this, LeaveStatus.class)));
        markAttendance.setOnClickListener(v -> startActivity(new Intent(adminhome.this, MarkAttendance.class)));
        addReportCard.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AddReportCard.class)));
        addReportCard.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AddReportCard.class)));
        sendFeedback.setOnClickListener(v -> startActivity(new Intent(adminhome.this, AdminFeedBack.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu from the XML file
        getMenuInflater().inflate(R.menu.admintop_app_bar, menu);
        return true;  // Return true to display the menu
    }
}
