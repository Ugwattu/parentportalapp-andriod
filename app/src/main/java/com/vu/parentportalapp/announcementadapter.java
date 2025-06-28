package com.vu.parentportalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class announcementadapter extends RecyclerView.Adapter<announcementviewholder> {

    Context context;
    List<announcement_item> items;

    public announcementadapter(Context context, List<announcement_item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public announcementviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new announcementviewholder(LayoutInflater.from(context).inflate(R.layout.announcement_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull announcementviewholder holder, int position) {
        holder.announcementname.setText(items.get(position).getAnnouncementName());
        holder.announcementdescription.setText(items.get(position).getAnnouncementDescription());
        holder.announcementdate.setText(items.get(position).getAnnouncementDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
