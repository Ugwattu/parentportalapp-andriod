package com.vu.parentportalapp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.compose.ui.platform.coreshims.ViewStructureCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AddReportCardViewHolder extends RecyclerView.ViewHolder {

    TextView sRollnoReportCard, studentNamesReportCard;
    EditText subjectNumber;

    public AddReportCardViewHolder(@NonNull View itemView) {
        super(itemView);
        sRollnoReportCard = itemView.findViewById(R.id.sRollnoReportCard);
        studentNamesReportCard = itemView.findViewById(R.id.studentNamesReportCard);
        subjectNumber = itemView.findViewById(R.id.subjectNumber_input);
    }
}
