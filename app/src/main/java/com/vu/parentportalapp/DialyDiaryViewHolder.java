package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DialyDiaryViewHolder extends RecyclerView.ViewHolder {
    TextView DailyDiaryDetail, DiaryDate;
    public DialyDiaryViewHolder(@NonNull View itemView) {
        super(itemView);
        DailyDiaryDetail = itemView.findViewById(R.id.dailydiaryDetail);
        DiaryDate = itemView.findViewById(R.id.diaryDate);
    }
}
