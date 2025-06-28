package com.vu.parentportalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class AdminFeedBack extends AppCompatActivity {

    RecyclerView recyclerView;
    Button ChatButton;
    MyDBHelper dbHelper;
    AdminFeedBackAdapter adapter;
    List<AdminFeedBackItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feed_back);

        MaterialToolbar toolbar = findViewById(R.id.feedbackAdmin_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        ChatButton = findViewById(R.id.adminFeedbackNewchat_btn);
        ChatButton.setOnClickListener(v -> {
            AdminFeedbackNewChat dialogFragment = new AdminFeedbackNewChat();
            dialogFragment.show(getSupportFragmentManager(), "NewChatDialog");
        });

        dbHelper = new MyDBHelper(this);
        items = dbHelper.getAdminFeedbackItems();
        Log.d("AdminFeedback", "Number of Feedbacks: " + items.size());

        recyclerView = findViewById(R.id.adminFeedbackRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminFeedBackAdapter(this, items, this::openfeedbackchat);
        recyclerView.setAdapter(adapter);
    }

    private void openfeedbackchat(AdminFeedBackItem adminFeedBackItem) {
        Intent intent = new Intent(this, Feedback.class);
        intent.putExtra("parentId", adminFeedBackItem.getParentId());
        startActivity(intent);
    }
}