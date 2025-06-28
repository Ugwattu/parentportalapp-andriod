package com.vu.parentportalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class LeaveStatusFragment extends DialogFragment {

    private static final String ARG_LEAVE_ID = "leave_id";
    private int leaveId;
    private MyDBHelper dbHelper;

    public static LeaveStatusFragment newInstance(int leaveId) {
        LeaveStatusFragment fragment = new LeaveStatusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEAVE_ID, leaveId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leaveId = getArguments().getInt(ARG_LEAVE_ID);
        }
        dbHelper = new MyDBHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_leave_status, container, false);

        // Initialize toolbar
        Toolbar toolbar = view.findViewById(R.id.leave_application_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close); // Set the close icon
        toolbar.setNavigationOnClickListener(v -> dismiss());

        MaterialTextView reasonText = view.findViewById(R.id.leave_reasonadmin);
        MaterialTextView leaveTypeText = view.findViewById(R.id.leave_typetitleadmin);
        MaterialTextView fromDateText = view.findViewById(R.id.leave_fromdateadmin);
        MaterialTextView tillDateText = view.findViewById(R.id.leave_tilldateadmin);
        AutoCompleteTextView statusDropdown = view.findViewById(R.id.autoComplete_leavestatusdropdown);
        MaterialButton submitButton = view.findViewById(R.id.leave_submit_btn);

        // Dropdown for Pending, Approved, Rejected
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                new String[]{"Pending", "Approved", "Rejected"});
        statusDropdown.setAdapter(adapter);

        // Fetch Leave Details
        Bundle leaveDetails = dbHelper.getLeaveDetailsById(leaveId);
        reasonText.setText(leaveDetails.getString("reason"));
        leaveTypeText.setText(leaveDetails.getString("leaveType"));
        fromDateText.setText(leaveDetails.getString("startDate"));
        tillDateText.setText(leaveDetails.getString("endDate"));
        statusDropdown.setText(leaveDetails.getString("status"), false);

        // Submit button action
        submitButton.setOnClickListener(v -> {
            String newStatus = statusDropdown.getText().toString();
            dbHelper.updateLeaveStatus(leaveId, newStatus);

            // If leave is approved, update attendance for all leave dates
            if ("Approved".equals(newStatus)) {
                // Fetch the student ID and leave dates from the leave details
                int studentId = leaveDetails.getInt("StudentID");
                String startDate = leaveDetails.getString("startDate");
                String endDate = leaveDetails.getString("endDate");

                // Update attendance for all dates between start and end date
                dbHelper.updateAttendanceForLeave(studentId, startDate, endDate, "Leave");
            }

            Toast.makeText(requireContext(), "Status updated!", Toast.LENGTH_SHORT).show();
            dismiss(); // Close fragment after update
        });

        return view;
    }
}
