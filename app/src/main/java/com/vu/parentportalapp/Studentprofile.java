package com.vu.parentportalapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class Studentprofile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentprofile);

//      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.stprofile_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_profile) {
                return true; // Already in profile
            } else if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else if (item.getItemId() == R.id.nav_announcemnet) {
                Intent intent = new Intent(this, Announcement.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }

            overridePendingTransition(0, 0);
            return true;
        });

        MyDBHelper dbHelper = new MyDBHelper(this);
        String[] studentData = dbHelper.getStudentDetails(Studentprofile.this);
        String[] parentData = dbHelper.getStoredParentDetails(Studentprofile.this);

        TextView studentNameTextView = findViewById(R.id.studentname);
        TextView studentDOBTextView = findViewById(R.id.studentDOB);
        TextView studentRollnoTextView = findViewById(R.id.studentRollno);
        TextView studentClassTextView = findViewById(R.id.studentclass);
        TextView studentSectionTextView = findViewById(R.id.studentSection);
        ImageView StProfileImgView = findViewById(R.id.studentProfilePic);

        TextView fatherNameTextView = findViewById(R.id.fatherName);
        TextView ContactTextView = findViewById(R.id.contact);
        TextView AddressTextView = findViewById(R.id.address);

        studentNameTextView.setText(studentData[0]); // Set Student Name
        studentClassTextView.setText(studentData[1]); // Set Student Class
        studentRollnoTextView.setText(studentData[5]); // Set Student Rollno
        studentDOBTextView.setText(studentData[2]); // Set Student DOB
        studentSectionTextView.setText(studentData[3]); // Set Student Section

        // Load Student Profile Image from File Path
        String profilePicPath = studentData[4];
        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            File imgFile = new File(profilePicPath);
            if (imgFile.exists()) {
                Bitmap studentBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                StProfileImgView.setImageBitmap(studentBitmap);
            } else {
                Log.e("Studentprofile", "Profile image file not found: " + profilePicPath);
                StProfileImgView.setImageResource(R.drawable.ic_studentprofile); // Default image
            }
        } else {
            Log.e("Studentprofile", "Profile image path is empty");
            StProfileImgView.setImageResource(R.drawable.ic_studentprofile); // Default image
        }

        fatherNameTextView.setText(parentData[0]); //Father Name
        ContactTextView.setText(parentData[1]); //showing fathername too
        AddressTextView.setText(parentData[4]); //address
    }
    @Override
    public void onBackPressed() {
        // ✅ Pressing back navigates to HomeActivity instead of exiting
        super.onBackPressed();
        Intent intent = new Intent(this, home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}