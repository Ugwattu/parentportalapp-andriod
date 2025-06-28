package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class LeaveStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDBHelper dbHelper;
    LeaveStatusAdapter adapter;
    List<LeaveStatusItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);

        MaterialToolbar toolbar = findViewById(R.id.leaveStatus_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        dbHelper = new MyDBHelper(this);
        items = dbHelper.getLeaves(); // Only pending leaves are fetched here

        Log.d("LeaveStatus", "Number of leaves: " + items.size());


        recyclerView = findViewById(R.id.leaveStatusRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LeaveStatusAdapter(this, items, this::openLeaveDialog);
        recyclerView.setAdapter(adapter);
    }

    public void openLeaveDialog(LeaveStatusItem leaveStatusItem) {
        // Pass the leaveId to the fragment
        LeaveStatusFragment fragment = LeaveStatusFragment.newInstance(leaveStatusItem.getLeaveId());
        fragment.show(getSupportFragmentManager(), "LeaveStatusFragment");
    }
}
