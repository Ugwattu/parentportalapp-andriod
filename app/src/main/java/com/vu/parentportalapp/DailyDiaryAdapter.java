package com.vu.parentportalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DailyDiaryAdapter extends RecyclerView.Adapter<DialyDiaryViewHolder> {
    private ArrayList<DailyDiaryItem> diaryList;

    public DailyDiaryAdapter(ArrayList<DailyDiaryItem> diaryList) {
        this.diaryList = diaryList;
    }
    @NonNull
    @Override
    public DialyDiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailydiary_item_view, parent, false);
        return new DialyDiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialyDiaryViewHolder holder, int position) {
        DailyDiaryItem item = diaryList.get(position);
        holder.DailyDiaryDetail.setText(item.getDiaryDescripton());
        holder.DiaryDate.setText(item.getDiaryDate());
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }
}
