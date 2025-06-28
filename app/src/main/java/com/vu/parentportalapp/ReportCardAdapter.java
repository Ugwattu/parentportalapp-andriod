package com.vu.parentportalapp;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportCardAdapter extends RecyclerView.Adapter<ReportCardAdapter.ViewHolder> {
    private List<ReportCardItem> reportCardList;

    public ReportCardAdapter(List<ReportCardItem> reportCardList) {
        this.reportCardList = reportCardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportCardItem item = reportCardList.get(position);
        holder.subject.setText(item.getSubject());
        holder.totalMarks.setText(String.valueOf(item.getMaxMarks()));
        holder.obtainedMarks.setText(String.valueOf(item.getMarksObtained()));
        holder.grade.setText(item.getGrade());

        if (item.getSubject().equals("Total")) {
            holder.subject.setTypeface(null, Typeface.BOLD);
            holder.totalMarks.setTypeface(null, Typeface.BOLD);
            holder.obtainedMarks.setTypeface(null, Typeface.BOLD);
            holder.grade.setTypeface(null, Typeface.BOLD);
        } else {
            holder.subject.setTypeface(null, Typeface.NORMAL);
            holder.totalMarks.setTypeface(null, Typeface.NORMAL);
            holder.obtainedMarks.setTypeface(null, Typeface.NORMAL);
            holder.grade.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return reportCardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, totalMarks, obtainedMarks, grade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.reportCardSubject);
            totalMarks = itemView.findViewById(R.id.reportCardTotalMarks);
            obtainedMarks = itemView.findViewById(R.id.reportCardObtMarks);
            grade = itemView.findViewById(R.id.reportCardGrade);
        }
    }
}
