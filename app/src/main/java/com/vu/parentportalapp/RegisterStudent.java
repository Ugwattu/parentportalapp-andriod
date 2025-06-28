package com.vu.parentportalapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

public class RegisterStudent extends AppCompatActivity {

    private EditText studentName, studentDOB, studentRollno, studentSection, parentName,
            parentContact, parentEmail, parentAddress;
    private AutoCompleteTextView studentClass;
    private Button saveButton;
    private MyDBHelper dbHelper;

    private final String[] classes = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        dbHelper = new MyDBHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.registerStudent_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        studentName = findViewById(R.id.studentname_input);
        studentDOB = findViewById(R.id.studentDOB_input);
        studentRollno = findViewById(R.id.studentRollNo_input);
        studentClass = findViewById(R.id.studentclass_input);
        studentSection = findViewById(R.id.studentsection_input);
        parentName = findViewById(R.id.parentname_input);
        parentContact = findViewById(R.id.parentcontact_input);
        parentEmail = findViewById(R.id.parentemail_input);
        parentAddress = findViewById(R.id.parentaddress_input);
        saveButton = findViewById(R.id.registerStudentSave_btn);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes);
        studentClass.setAdapter(classAdapter);

        studentDOB.setFocusable(false);
        studentDOB.setClickable(true);
        studentDOB.setOnClickListener(v -> showMaterialDatePicker());

        saveButton.setOnClickListener(view -> {
            String sName = studentName.getText().toString().trim();
            String sDOB = studentDOB.getText().toString().trim();
            String sRollno = studentRollno.getText().toString().trim();
            String sClass = studentClass.getText().toString().trim();
            String sSection = studentSection.getText().toString().trim();
            String pName = parentName.getText().toString().trim();
            String pContact = parentContact.getText().toString().trim();
            String pEmail = parentEmail.getText().toString().trim();
            String pAddress = parentAddress.getText().toString().trim();

            if (sName.isEmpty() || sDOB.isEmpty() || sClass.isEmpty() || sSection.isEmpty()
                    || pName.isEmpty() || pContact.isEmpty() || pEmail.isEmpty() || pAddress.isEmpty()) {
                Toast.makeText(RegisterStudent.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Arrays.asList(classes).contains(sClass)) {
                Toast.makeText(RegisterStudent.this, "Invalid class selected", Toast.LENGTH_SHORT).show();
                return;
            }

            long parentId = dbHelper.insertParent(pName, pContact, pEmail, "default123", pAddress);
            if (parentId == -1) {
                Toast.makeText(RegisterStudent.this, "Error adding parent", Toast.LENGTH_SHORT).show();
                return;
            }

            long studentId = dbHelper.insertStudent(sName, sDOB, (int) parentId, sClass, sSection, sRollno);
            if (studentId != -1) {
                Toast.makeText(RegisterStudent.this, "Student Registered Successfully", Toast.LENGTH_SHORT).show();
                // Clear fields
                studentName.setText("");
                studentDOB.setText("");
                studentRollno.setText("");
                studentClass.setText("");
                studentSection.setText("");
                parentName.setText("");
                parentContact.setText("");
                parentEmail.setText("");
                parentAddress.setText("");
                finish();
            } else {
                Toast.makeText(RegisterStudent.this, "Error adding student", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showMaterialDatePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date of Birth")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = sdf.format(selection);
            studentDOB.setText(formattedDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
