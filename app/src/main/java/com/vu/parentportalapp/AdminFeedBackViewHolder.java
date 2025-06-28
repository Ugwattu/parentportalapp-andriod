package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminFeedBackViewHolder  extends RecyclerView.ViewHolder {

    TextView fatherName, studentName, studentClass;
    public AdminFeedBackViewHolder(@NonNull View itemView) {
        super(itemView);

        fatherName = itemView.findViewById(R.id.feedbackFatherNameRecylcer);
        studentName = itemView.findViewById(R.id.feedbackStudentNameRecylcer);
        studentClass = itemView.findViewById(R.id.feedbackStudentClassRecylcer);
    }
}
