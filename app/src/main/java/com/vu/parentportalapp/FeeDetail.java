package com.vu.parentportalapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FeeDetail extends AppCompatActivity {
    private MyDBHelper dbHelper;
    private FeeDetailAdapter adapter;
    private List<feedetail_item> feeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);

//      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.feedetail_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        dbHelper = new MyDBHelper(this);
        List<Map<String, String>> feeitems = dbHelper.getFeeDetailsWithMonth(this);
        Collections.reverse(feeitems);
        Log.d("FeeDetailActivity", "Total Fee Details retrieved: " + feeitems.size());

        List<feedetail_item> Items = convertToFeedetailItems(feeitems);
        Log.d("FeeDetailActivity", "Converted Fee Items list size: " + Items.size());

        RecyclerView recyclerView = findViewById(R.id.feeDetailRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FeeDetailAdapter(this, Items);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
    // Convert List<Map<String, String>> to List<feedetail_item>
    private List<feedetail_item> convertToFeedetailItems(List<Map<String, String>> items) {
        List<feedetail_item> feeItems = new ArrayList<>();

        for (Map<String, String> item : items) {
            feedetail_item feeItem = new feedetail_item();
            // Assuming feedetail_item has setters for each field
            feeItem.setMonth(item.get("Month"));  // Replace with actual field names
            feeItem.setFeeType(item.get("FeeType"));
            feeItem.setDueDate(item.get("DueDate"));
            feeItem.setPaidDate(item.get("PaidDate"));
            feeItem.setAmount(item.get("Amount"));
            feeItem.setStatus(item.get("Status"));
            feeItems.add(feeItem);

        }
        // Log each converted item to ensure correct data
        for (feedetail_item feeItem : feeItems) {
            Log.d("FeeDetailActivity", "Converted Fee Item: " + feeItem.toString());
        }

        return feeItems;
    }
}