package com.vu.parentportalapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class TransportFacility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_facility);

//      toolbar backbutton logic
        MaterialToolbar toolbar = findViewById(R.id.transport_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });

        MyDBHelper dbHelper = new MyDBHelper(this);
        String[] transportData = dbHelper.getTransportDetails(TransportFacility.this);

        TextView driverNameTextView = findViewById(R.id.driverName);
        TextView driverContactTextView = findViewById(R.id.driverContact);
        TextView busNumberTextView = findViewById(R.id.busNumber);
        TextView pickupPointTextView = findViewById(R.id.pickupPoint);
        TextView dropPointTextView = findViewById(R.id.dropPoint);

        driverNameTextView.setText(transportData[1]);
        driverContactTextView.setText(transportData[2]);
        busNumberTextView.setText(transportData[0]);
        pickupPointTextView.setText(transportData[3]);
        dropPointTextView.setText(transportData[4]);
    }
}