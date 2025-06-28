package com.vu.parentportalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeeDetailAdapter extends RecyclerView.Adapter<feeDetailviewholder> {

    Context context;
    List<feedetail_item> items;

    public FeeDetailAdapter(Context context, List<feedetail_item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public feeDetailviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new feeDetailviewholder(LayoutInflater.from(context).inflate(R.layout.feedetail_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull feeDetailviewholder holder, int position) {
        holder.FeeMonth.setText(items.get(position).getMonth());
        holder.FeeType.setText(items.get(position).getFeeType());
        holder.DueDate.setText(items.get(position).getDueDate());
        holder.PaidDate.setText(items.get(position).getPaidDate());
        holder.Amount.setText(items.get(position).getAmount());
        holder.Status.setText(items.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
