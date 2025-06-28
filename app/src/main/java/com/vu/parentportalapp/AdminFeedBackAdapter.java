package com.vu.parentportalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminFeedBackAdapter extends RecyclerView.Adapter<AdminFeedBackViewHolder> {

    private Context context;
    private List<AdminFeedBackItem> feedbackList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AdminFeedBackItem item);
    }

    public AdminFeedBackAdapter(Context context, List<AdminFeedBackItem> items, AdminFeedBackAdapter.OnItemClickListener listener) {
        this.context = context;
        this.feedbackList = items;  // Fix the assignment here, using 'items' instead of 'leaveList'
        this.listener = listener;
    }


    @NonNull
    @Override
    public AdminFeedBackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminFeedBackViewHolder(LayoutInflater.from(context).inflate(R.layout.item_admin_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFeedBackViewHolder holder, int position) {
        AdminFeedBackItem item = feedbackList.get(position);

        holder.fatherName.setText(item.getFatherName());
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
        return feedbackList.size();
    }
}
