package com.vu.parentportalapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class AddTransport extends AppCompatActivity {

    EditText addDriverName, addDriverContact, addBusNumber, addPickup, addDrop;
    AutoCompleteTextView studentClass, selectStudent;
    Button saveButton;
    MyDBHelper dbHelper;
    String selectedClass = "", selectedStudent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);

        MaterialToolbar toolbar = findViewById(R.id.addTransport_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        dbHelper = new MyDBHelper(this);

        studentClass = findViewById(R.id.autoComplete_ClassType);
        selectStudent = findViewById(R.id.autoComplete_Student);
        addDriverName = findViewById(R.id.driverName_input);
        addDriverContact = findViewById(R.id.driverContact_input);
        addBusNumber = findViewById(R.id.busNumber_input);
        addPickup = findViewById(R.id.pickUp_input);
        addDrop = findViewById(R.id.drop_input);
        saveButton = findViewById(R.id.transportSave_btn);

        loadClassDropdown();

        studentClass.setOnItemClickListener((parent, view, position, id) -> {
            selectedClass = parent.getItemAtPosition(position).toString();
            loadStudentDropdown(selectedClass);
        });

        selectStudent.setOnItemClickListener((parent, view, position, id) -> {
            selectedStudent = parent.getItemAtPosition(position).toString();
        });

        saveButton.setOnClickListener(v -> saveTransportData());
    }

    private void loadClassDropdown() {
        List<String> classList = dbHelper.getAllClasses();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classList);
        studentClass.setAdapter(adapter);
    }

    private void loadStudentDropdown(String className) {
        List<String> studentList = dbHelper.getStudentsByClass(className);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, studentList);
        selectStudent.setAdapter(adapter);
    }

    private void saveTransportData() {
        if (selectedClass.isEmpty() || selectedStudent.isEmpty()) {
            Toast.makeText(this, "Please select class and student", Toast.LENGTH_SHORT).show();
            return;
        }

        int studentId = dbHelper.getStudentId(selectedStudent, selectedClass);
        if (studentId == -1) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String driverName = addDriverName.getText().toString().trim();
        String driverContact = addDriverContact.getText().toString().trim();
        String busNumber = addBusNumber.getText().toString().trim();
        String pickup = addPickup.getText().toString().trim();
        String drop = addDrop.getText().toString().trim();

        if (driverName.isEmpty() || driverContact.isEmpty() || busNumber.isEmpty() || pickup.isEmpty() || drop.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.insertTransportData(studentId, busNumber, driverName, driverContact, pickup, drop);
        if (success) {
            Toast.makeText(this, "Transport details saved", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity or reset fields
        } else {
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }
}
