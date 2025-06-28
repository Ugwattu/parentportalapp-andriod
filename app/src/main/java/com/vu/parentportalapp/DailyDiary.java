package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DailyDiary extends AppCompatActivity {
    private RecyclerView dailyRecyclerView;
    private DailyDiaryAdapter dailyAdapter;
    private ArrayList<DailyDiaryItem> diaryList;
    private TabLayout dailyTabs;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

//      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.dailydiary_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        dailyRecyclerView = findViewById(R.id.dailyRecyclerView);
        dailyTabs = findViewById(R.id.dailyTabs);
        dbHelper = new MyDBHelper(this);

        diaryList = new ArrayList<>();
        dailyAdapter = new DailyDiaryAdapter(diaryList);
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dailyRecyclerView.setAdapter(dailyAdapter);

        loadDiaryData("Classwork");

        // Handle Tab Selection
        dailyTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadDiaryData("Classwork");
                        break;
                    case 1:
                        loadDiaryData("Homework");
                        break;
                    case 2:
                        loadDiaryData("Assignment");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void loadDiaryData(String columnName) {
        diaryList.clear();
        List<Map<String, String>> allEntries = dbHelper.getDailyDiaryEntries(this);

        Map<String, StringBuilder> groupedEntries = new LinkedHashMap<>();

        for (Map<String, String> entry : allEntries) {
            String date = entry.get("Date");
            String details = entry.get(columnName);

            if (details != null && !details.trim().isEmpty()) {
                if (!groupedEntries.containsKey(date)) {
                    groupedEntries.put(date, new StringBuilder(details));
                } else {
                    groupedEntries.get(date).append("\n").append(details);
                }
            }
        }
        for (Map.Entry<String, StringBuilder> groupedEntry : groupedEntries.entrySet()) {
//            Key is date, value is description
            diaryList.add(new DailyDiaryItem(groupedEntry.getValue().toString(), groupedEntry.getKey()));
        }

        dailyAdapter.notifyDataSetChanged();
    }
}