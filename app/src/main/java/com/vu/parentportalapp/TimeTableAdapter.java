package com.vu.parentportalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {

    private ArrayList<TimeTableItem> timetableList;

    public TimeTableAdapter(ArrayList<TimeTableItem> timetableList) {
        this.timetableList = timetableList;
    }

    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item_view, parent, false);
        return new TimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder holder, int position) {
        TimeTableItem item = timetableList.get(position);
        holder.TimeTableSubject.setText(item.getTimeTableSubject());
        holder.TimeTableTeacher.setText(item.getTimeTableTeacher());
        holder.TimeTableDuration.setText(item.getTimeTableDuration());
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }
}
