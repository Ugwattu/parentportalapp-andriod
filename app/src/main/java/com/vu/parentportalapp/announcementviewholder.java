package com.vu.parentportalapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class announcementviewholder extends RecyclerView.ViewHolder {
    TextView announcementname, announcementdescription, announcementdate;
    public announcementviewholder(@NonNull View itemView) {
        super(itemView);
        announcementname = itemView.findViewById(R.id.announcement_name);
        announcementdescription = itemView.findViewById(R.id.announcement_description);
        announcementdate = itemView.findViewById(R.id.announcement_date);
    }
}
