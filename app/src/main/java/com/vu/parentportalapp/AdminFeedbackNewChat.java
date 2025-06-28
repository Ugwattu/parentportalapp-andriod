package com.vu.parentportalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class AdminFeedbackNewChat extends DialogFragment {

    public AdminFeedbackNewChat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_feedback_new_chat, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.newFeedbacktoolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());

        MaterialAutoCompleteTextView classDropdown = view.findViewById(R.id.autoComplete_classFeedback);
        LinearLayout studentListContainer = view.findViewById(R.id.studentListContainer);

        MyDBHelper dbHelper = new MyDBHelper(requireContext());

        // Populate class dropdown
        List<String> classList = dbHelper.getAllClasses();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, classList);
        classDropdown.setAdapter(adapter);

        classDropdown.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedClass = parent.getItemAtPosition(position).toString();
            List<AdminFeedbackNewChatItem> students = dbHelper.getStudentsByClassDetailed(selectedClass);

            studentListContainer.removeAllViews();
            LayoutInflater itemInflater = LayoutInflater.from(requireContext());

            for (AdminFeedbackNewChatItem student : students) {
                View studentItem = itemInflater.inflate(R.layout.item_adminfeedbacknewchat, studentListContainer, false);
                TextView studentText = studentItem.findViewById(R.id.studentText);
                studentText.setText(student.rollNo + ". " + student.name);

                studentItem.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), Feedback.class);
                    intent.putExtra("parentId", student.parentId);
                    startActivity(intent);
                });

                studentListContainer.addView(studentItem);
            }
        });

        return view;
    }
}
