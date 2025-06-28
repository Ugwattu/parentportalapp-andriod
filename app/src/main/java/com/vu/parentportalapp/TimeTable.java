package com.vu.parentportalapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable extends AppCompatActivity {
    private RecyclerView timeTableRecyclerView;
    private TimeTableAdapter timeTableAdapter;
    private ArrayList<TimeTableItem> timetableList;
    private TabLayout timetableTabs;
    private MyDBHelper dbHelper;
    private Map<String, ArrayList<TimeTableItem>> timetableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        //      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.timetable_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });
        timeTableRecyclerView = findViewById(R.id.timeTableRecycler);
        timetableTabs = findViewById(R.id.timeTableTabs);
        dbHelper = new MyDBHelper(this);

        timetableList = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(timetableList);
        timeTableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        timeTableRecyclerView.setAdapter(timeTableAdapter);

        timetableData = loadTimetableFromDB();
        loadTimeTableData("Mon");
        timetableTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedDay = tab.getText().toString();
                loadTimeTableData(selectedDay);
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Map<String, ArrayList<TimeTableItem>> loadTimetableFromDB() {
        Map<String, ArrayList<TimeTableItem>> structuredData = new HashMap<>();
        List<Map<String, String>> rawTimetable = dbHelper.getTimeTable(this);

        for (Map<String, String> entry : rawTimetable) {
            String day = entry.get("Day");
            String timeSlot = entry.get("TimeSlot");
            String subject = entry.get("Subject");
            String teacher = entry.get("Teacher");

            // mapping: time , teacher, subject
            TimeTableItem item = new TimeTableItem(timeSlot, teacher, subject);

            if (!structuredData.containsKey(day)) {
                structuredData.put(day, new ArrayList<>());
            }
            structuredData.get(day).add(item);
        }

        return structuredData;
    }


    private void loadTimeTableData(String day) {
        timetableList.clear(); // Clear old data

        if (timetableData.containsKey(day)) {
            timetableList.addAll(timetableData.get(day));
        }

        timeTableAdapter.notifyDataSetChanged();
    }
}