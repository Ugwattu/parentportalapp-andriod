package com.vu.parentportalapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddReportCardAdapter extends RecyclerView.Adapter<AddReportCardViewHolder>{

    private Context context;
    private List<AddReportCardItem> items;

    public AddReportCardAdapter(List<AddReportCardItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AddReportCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddReportCardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_reportcard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddReportCardViewHolder holder, int position) {
        AddReportCardItem item = items.get(position);

        // Bind the data to the ViewHolder
        holder.sRollnoReportCard.setText(item.getRollNo());
        holder.studentNamesReportCard.setText(item.getStudentName());
        holder.subjectNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setAddSubjectNumber(s.toString()); // update model live
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    public List<AddReportCardItem> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
