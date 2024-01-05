package com.unitbv.siipa.reviews;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {
    @Query("SELECT * from reviews where destinationId = :destinationId")
    List<Review> getReviewsByDestinationId(Long destinationId);

    @Insert
    void addReview(Review review);
}