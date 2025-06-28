package com.vu.parentportalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceRecordAdapter extends RecyclerView.Adapter<AttendanceRecordViewHolder> {

    private List<AttendanceRecordItem> attendanceRecordList;

    public AttendanceRecordAdapter(List<AttendanceRecordItem> attendanceRecordList) {
        this.attendanceRecordList = attendanceRecordList;
    }

    @NonNull
    @Override
    public AttendanceRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_record,parent,false);
        return new AttendanceRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceRecordViewHolder holder, int position) {
        AttendanceRecordItem item = attendanceRecordList.get(position);
        holder.date.setText(item.getDate());
        holder.day.setText(item.getDay());
        holder.status.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceRecordList.size();
    }
}
