package com.vu.parentportalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Announcement extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private MyDBHelper dbHelper;
    private announcementadapter adapter;
    private List<announcement_item> announcementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        dbHelper = new MyDBHelper(this);

        // Toolbar back button logic
        MaterialToolbar toolbar = findViewById(R.id.announcement_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.nav_announcemnet);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_announcemnet) {
                return true; // Already in announcements
            } else if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else if (item.getItemId() == R.id.nav_profile) {
                Intent intent = new Intent(this, Studentprofile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }

            overridePendingTransition(0, 0);
            return true;
        });

        // Fetch announcements from the database
        List<announcement_item> items = dbHelper.getAnnouncements();
        Collections.reverse(items);
        Log.d("AnnouncementActivity", "Total announcements retrieved: " + items.size());

        // Setting up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.announcementRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        announcementadapter adapter = new announcementadapter(this, items);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
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
