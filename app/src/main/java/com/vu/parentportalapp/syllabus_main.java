package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class syllabus_main extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_main);

        // Toolbar back button logic
        MaterialToolbar toolbar = findViewById(R.id.syllabus_main_toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        expandableListView = findViewById(R.id.expandable_Syllabus);

        // Initialize database helper
        MyDBHelper dbHelper = new MyDBHelper(this);
        List<Map<String, Object>> syllabusData = dbHelper.getSyllabusData(this);

        // Convert data to match SimpleExpandableListAdapter format
        List<Map<String, String>> parentList = new ArrayList<>();  // Subjects
        List<List<Map<String, String>>> childList = new ArrayList<>(); // Topics under subjects

        for (Map<String, Object> subjectMap : syllabusData) {
            // Extract subject
            String subject = (String) subjectMap.get("subject");
            List<Map<String, String>> topicItems = new ArrayList<>();

            // Create parent map
            Map<String, String> parent = new HashMap<>();
            parent.put("subject", subject);
            parentList.add(parent);

            // Extract topic list
            List<Map<String, String>> topics = (List<Map<String, String>>) subjectMap.get("topic_list");
            if (topics != null) {
                topicItems.addAll(topics);
            }
            childList.add(topicItems);
        }

        // Create adapter
        ExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                parentList,
                android.R.layout.simple_expandable_list_item_1, // Parent layout (subject)
                new String[]{"subject"},
                new int[]{android.R.id.text1},
                childList,
                android.R.layout.simple_list_item_1, // Child layout (topics)
                new String[]{"topics"},
                new int[]{android.R.id.text1}
        );

        // Set adapter to ExpandableListView
        expandableListView.setAdapter(adapter);
    }
}
