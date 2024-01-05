package com.unitbv.siipa.reviews;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitbv.siipa.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView userTextView;
    TextView dateTextView;
    TextView commentTextView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        userTextView = itemView.findViewById(R.id.textViewUser);
        dateTextView = itemView.findViewById(R.id.textViewDate);
        commentTextView = itemView.findViewById(R.id.textViewComment);
    }
}
