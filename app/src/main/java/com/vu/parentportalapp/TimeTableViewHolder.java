package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableViewHolder  extends RecyclerView.ViewHolder {
    TextView TimeTableSubject, TimeTableTeacher, TimeTableDuration;
    public TimeTableViewHolder(@NonNull View itemView) {
        super(itemView);
        TimeTableSubject = itemView.findViewById(R.id.timeTableSubject);
        TimeTableTeacher = itemView.findViewById(R.id.timeTableTeacher);
        TimeTableDuration = itemView.findViewById(R.id.timetableDuration);
    }
}
