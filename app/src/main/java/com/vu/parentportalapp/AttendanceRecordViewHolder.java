package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceRecordViewHolder extends RecyclerView.ViewHolder {

    TextView date, day, status;
    public AttendanceRecordViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.attendanceDate);
        day = itemView.findViewById(R.id.attendanceDay);
        status = itemView.findViewById(R.id.attendanceStatus);
    }
}
