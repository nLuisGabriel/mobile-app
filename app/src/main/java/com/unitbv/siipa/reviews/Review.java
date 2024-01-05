package com.unitbv.siipa.reviews;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.user.User;

import java.time.LocalDate;

@Entity(tableName = "reviews",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Destination.class,
                        parentColumns = "id",
                        childColumns = "destinationId")
        })
public class Review {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private LocalDate creationDate;
    private String comment;
    @NonNull
    private Long userId;
    @NonNull
    private Long destinationId;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }
}
