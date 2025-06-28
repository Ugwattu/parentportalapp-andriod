package com.vu.parentportalapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vu.parentportalapp.R;
import java.util.List;

public class MarkAttendanceAdapter extends RecyclerView.Adapter<MarkAttendanceViewHolder> {

    private final List<MarkAttendanceItem> items;

    public MarkAttendanceAdapter(List<MarkAttendanceItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MarkAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarkAttendanceViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mark_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarkAttendanceViewHolder holder, int position) {
        MarkAttendanceItem item = items.get(position);

        Log.d("RecyclerView", "Binding data: RollNo: " + item.getRollNo() + ", Name: " + item.getStudentName() + ", Status: " + item.getAttendanceStatus());

        holder.sRollno.setText(item.getRollNo());
        holder.sName.setText(item.getStudentName());

        boolean isPresent = "Present".equalsIgnoreCase(item.getAttendanceStatus());

        // Remove text from switch
        holder.materialSwitch.setText("");

        // Set initial switch state
        holder.materialSwitch.setChecked(isPresent);

        // Set initial color
        updateSwitchThumbColor(holder, isPresent);

        // Switch listener to change thumb color when toggled
        holder.materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSwitchThumbColor(holder, isChecked);
        });
    }

    private void updateSwitchThumbColor(MarkAttendanceViewHolder holder, boolean isChecked) {
        if (isChecked) {
            // Only circle (thumb) becomes blue
            holder.materialSwitch.setThumbTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.blue));
            holder.materialSwitch.setTrackTintList(null); // Keep track normal
        } else {
            // Only circle (thumb) becomes red
            holder.materialSwitch.setThumbTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.red));
            holder.materialSwitch.setTrackTintList(null); // Keep track normal
        }
    }


    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<MarkAttendanceItem> getItems() {
        return items;
    }

}
