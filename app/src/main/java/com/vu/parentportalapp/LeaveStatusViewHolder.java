package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaveStatusViewHolder extends RecyclerView.ViewHolder {

    TextView studentName, studentClass;
    public LeaveStatusViewHolder(@NonNull View itemView) {
        super(itemView);

        studentName = itemView.findViewById(R.id.leaveStudentNameRecylcer);
        studentClass = itemView.findViewById(R.id.leaveStudentClassRecylcer);
    }
}
