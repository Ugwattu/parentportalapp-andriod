package com.vu.parentportalapp;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;


public class Feedback extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private EditText editMessage;
    private Button sendButton;
    private RecyclerView recyclerView;

    private int parentId = -1;
    private String senderType = "";
    private List<FeedbackMessage> messageList;
    private FeedbackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        MaterialToolbar toolbar = findViewById(R.id.feedback_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });


        dbHelper = new MyDBHelper(this);
        editMessage = findViewById(R.id.edit_gchat_message);
        sendButton = findViewById(R.id.button_chat_send);
        recyclerView = findViewById(R.id.recycler_gchat);

        SharedPreferences prefs = getSharedPreferences("ParentPortalPrefs", MODE_PRIVATE);

        // Detect whether admin is currently active via Intent flag or context
        if (prefs.contains("admin_id") && getIntent().hasExtra("parentId")) {
            // Admin is logged in and selected a parent
            senderType = "admin";
            parentId = getIntent().getIntExtra("parentId", -1);
            Log.d("Feedback", "Admin detected. ParentID from intent: " + parentId);

            if (parentId == -1) {
                Toast.makeText(this, "No parent selected", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else if (prefs.contains("parent_id")) {
            // Parent is logged in
            senderType = "parent";
            parentId = prefs.getInt("parent_id", -1);
            Log.d("Feedback", "Parent detected. ParentID from prefs: " + parentId);
        } else {
            // Neither role is logged in
            Toast.makeText(this, "No valid user session found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }




        // Setup RecyclerView
        messageList = new ArrayList<>();
        adapter = new FeedbackAdapter(messageList, senderType); // You pass senderType to align messages
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadMessages();

        sendButton.setOnClickListener(v -> {
            String message = editMessage.getText().toString().trim();
            if (message.isEmpty()) {
                Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
                return;
            }

            sendFeedback(message, parentId, senderType);
            editMessage.setText("");
        });
    }

    private void sendFeedback(String message, int parentId, String sender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Parent_ID", parentId);
        values.put("Feedback", message);
        values.put("Sender", sender);

        long result = db.insert("Feedback", null, values);

        if (result != -1) {
            loadMessages(); // Refresh chat
        } else {
            Toast.makeText(this, "Error sending message", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMessages() {
        messageList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT Feedback, Sender, Time FROM Feedback WHERE Parent_ID = ? ORDER BY Time ASC",
                new String[]{String.valueOf(parentId)}
        );

        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndexOrThrow("Feedback"));
                String sender = cursor.getString(cursor.getColumnIndexOrThrow("Sender"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("Time"));

                messageList.add(new FeedbackMessage(message, sender, time));
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageList.size() - 1); // Scroll to latest message
    }
}
