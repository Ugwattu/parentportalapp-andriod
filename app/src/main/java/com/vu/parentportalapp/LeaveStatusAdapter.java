package com.vu.parentportalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusViewHolder> {

    private Context context;
    private List<LeaveStatusItem> leaveList;
    private OnItemClickListener listener;  // This will hold the callback reference

    // Interface to handle item click
    public interface OnItemClickListener {
        void onItemClick(LeaveStatusItem item);
    }

    // Constructor now accepts the OnItemClickListener
    public LeaveStatusAdapter(Context context, List<LeaveStatusItem> items, OnItemClickListener listener) {
        this.context = context;
        this.leaveList = items;  // Fix the assignment here, using 'items' instead of 'leaveList'
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeaveStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item view and return the ViewHolder
        return new LeaveStatusViewHolder(LayoutInflater.from(context).inflate(R.layout.item_leavestatus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveStatusViewHolder holder, int position) {
        LeaveStatusItem item = leaveList.get(position);

        // Bind the data to the ViewHolder
        holder.studentName.setText(item.getStudentName());
        holder.studentClass.setText(item.getStudentClass());

        // Set the click listener on the itemView
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);  // Call the callback when an item is clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }
}
