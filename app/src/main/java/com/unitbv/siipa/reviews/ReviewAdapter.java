package com.unitbv.siipa.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.user.User;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<Review> reviews;

    private Context context;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_review_layout, parent, false);
        context = parent.getContext();
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        User reviewUser = ApplicationRoomDatabase.getDatabase(context).userDao().getUserById(review.getUserId());

        holder.userTextView.setText(reviewUser.getUsername());
        holder.dateTextView.setText(review.getCreationDate().toString());
        holder.commentTextView.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviewList) {
        this.reviews = reviewList;
    }
}
