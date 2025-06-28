package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class feeDetailviewholder extends RecyclerView.ViewHolder {

    TextView FeeMonth, FeeType, DueDate, PaidDate, Amount, Status;
    public feeDetailviewholder(@NonNull View itemView) {
        super(itemView);
        FeeMonth = itemView.findViewById(R.id.feeMonth);
        FeeType = itemView.findViewById(R.id.feeType);
        DueDate = itemView.findViewById(R.id.feeDueDate);
        PaidDate = itemView.findViewById(R.id.feePaidDate);
        Amount = itemView.findViewById(R.id.feeAmount);
        Status = itemView.findViewById(R.id.feeStatus);
    }
}
