package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ReportCard extends AppCompatActivity {
    private MyDBHelper myDBHelper;
    private AutoCompleteTextView autoCompleteReportCard;
    private RecyclerView recyclerView;
    private ReportCardAdapter reportCardAdapter;
    private List<ReportCardItem> reportCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card);

        //      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.reportcard_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        myDBHelper = new MyDBHelper(this);
        autoCompleteReportCard = findViewById(R.id.autoComplete_reportcard);
        recyclerView = findViewById(R.id.recyclerViewReportCard);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportCardList = new ArrayList<>();

        reportCardAdapter = new ReportCardAdapter(reportCardList);
        recyclerView.setAdapter(reportCardAdapter);

        List<String> termList = myDBHelper.getTermsList(this);

        // Populate the dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, termList);
        autoCompleteReportCard.setAdapter(adapter);

        // Set item click listener to fetch and display attendance counts for selected month-year
        autoCompleteReportCard.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTerm = (String) parent.getItemAtPosition(position);
            displayReportCardData(selectedTerm);
        });
    }

    private void displayReportCardData(String selectedTerm) {
        reportCardList.clear();
        List<ReportCardItem> reportCardRecords = myDBHelper.getReportCard(this, selectedTerm);

        int totalObtainedMarks = 0;
        int totalMaxMarks = 0;

        for (ReportCardItem record : reportCardRecords) {
            Log.d("ReportCard", "Subject: " + record.getSubject() + " Marks: " + record.getMarksObtained());

            totalMaxMarks += record.getMaxMarks();
            totalObtainedMarks += record.getMarksObtained();

            reportCardList.add(new ReportCardItem(
                    record.getTerm(),
                    record.getSubject(),
                    record.getMaxMarks(),
                    record.getMarksObtained(),
                    record.getGrade()
            ));
        }
        double percentage = (totalObtainedMarks * 100.0) / totalMaxMarks;
        String overallGrade = myDBHelper.getGrade(percentage);

        reportCardList.add(new ReportCardItem(
                "Overall",
                "Total",
                totalMaxMarks,
                totalObtainedMarks,
                overallGrade
        ));

        reportCardAdapter.notifyDataSetChanged();
    }
}
