package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;

public class MarkAttendanceViewHolder extends RecyclerView.ViewHolder {

    TextView sRollno, sName;
    MaterialSwitch materialSwitch;
    public MarkAttendanceViewHolder(@NonNull View itemView) {
        super(itemView);
        sRollno = itemView.findViewById(R.id.sRollno);
        sName = itemView.findViewById(R.id.studentName);
        materialSwitch = itemView.findViewById(R.id.attendanceStatus_switch);
    }
}
