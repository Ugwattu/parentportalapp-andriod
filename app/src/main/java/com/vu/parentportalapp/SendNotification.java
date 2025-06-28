package com.vu.parentportalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class SendNotification extends AppCompatActivity {
    private TextInputEditText titleInput, descriptionInput;
    private Button submitButton;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        MaterialToolbar toolbar = findViewById(R.id.sendNotification_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });


        dbHelper = new MyDBHelper(this);

        titleInput = findViewById(R.id.titleEditText);
        descriptionInput = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitNotification_btn);

        submitButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(SendNotification.this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbHelper.insertAnnouncement(title, description);
                if (isInserted) {
                    Toast.makeText(SendNotification.this, "Announcement saved successfully", Toast.LENGTH_SHORT).show();
                    titleInput.setText("");
                    descriptionInput.setText("");
                } else {
                    Toast.makeText(SendNotification.this, "Failed to save announcement", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
