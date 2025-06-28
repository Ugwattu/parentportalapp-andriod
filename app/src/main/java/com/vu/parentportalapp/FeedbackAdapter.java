package com.vu.parentportalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_USER = 1;
    private static final int VIEW_TYPE_ADMIN = 2;

    private List<FeedbackMessage> messageList;
    private String senderType;  // to track the sender type (either "parent" or "admin")

    // Constructor to accept both the message list and the sender type
    public FeedbackAdapter(List<FeedbackMessage> messageList, String senderType) {
        this.messageList = messageList;
        this.senderType = senderType;
    }

    @Override
    public int getItemViewType(int position) {
        // Get the sender of the current message
        FeedbackMessage message = messageList.get(position);

        // If the sender in the message matches the senderType, it's the correct view type
        if (message.getSender().equals(senderType)) {
            return VIEW_TYPE_USER; // View for the logged-in user (parent/admin)
        } else {
            return VIEW_TYPE_ADMIN; // View for the other party (admin/parent)
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_USER) {
            // Inflate the layout for the logged-in user (either parent or admin)
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_send_message, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            // Inflate the layout for the other party (admin or parent)
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_receive_message, parent, false);
            return new AdminMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedbackMessage message = messageList.get(position);

        if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).bind(message);
        } else if (holder instanceof AdminMessageViewHolder) {
            ((AdminMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // ViewHolder for the user message (parent or admin)
    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageView, timeView;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            messageView = itemView.findViewById(R.id.sendTextMessage);
            timeView = itemView.findViewById(R.id.SentMessageTime);
        }

        void bind(FeedbackMessage message) {
            messageView.setText(message.getMessage());
            timeView.setText("You • " + message.getTime());
        }
    }

    // ViewHolder for the admin message (or the opposite party)
    static class AdminMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageView, timeView;

        AdminMessageViewHolder(View itemView) {
            super(itemView);
            messageView = itemView.findViewById(R.id.receiveTextMessage);
            timeView = itemView.findViewById(R.id.ReceiveMessageTime);
        }

        void bind(FeedbackMessage message) {
            messageView.setText(message.getMessage());
            timeView.setText("Admin • " + message.getTime());
        }
    }
}
