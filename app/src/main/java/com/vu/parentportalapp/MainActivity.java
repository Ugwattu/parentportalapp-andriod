package com.vu.parentportalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyDBHelper dbHelper;

    // Login activity variables
    EditText username, password;
    AutoCompleteTextView userTypeDropdown;
    Button loginButton;
    String selectedUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_btn);
        userTypeDropdown = findViewById(R.id.autoComplete_userType);

        dbHelper = new MyDBHelper(this);

        // Set up the dropdown menu
        String[] userTypes = {"Parent", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, userTypes);
        userTypeDropdown.setAdapter(adapter);

        // Handle dropdown selection
        userTypeDropdown.setOnItemClickListener((parent, view, position, id) -> selectedUserType = userTypes[position]);

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // Validation checks
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Check login based on user type
                if (dbHelper.checkLogin(selectedUserType, email, pass, MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to the respective home screen
                    if (selectedUserType.equals("Parent")) {
                        startActivity(new Intent(MainActivity.this, home.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, adminhome.class));
                    }
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
