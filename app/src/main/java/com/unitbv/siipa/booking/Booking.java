package com.unitbv.siipa.booking;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.user.User;

import java.time.LocalDate;

@Entity(tableName = "bookings",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Destination.class,
                        parentColumns = "id",
                        childColumns = "destinationId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    @NonNull
    private Long userId;

    @NonNull
    private Long destinationId;

    @NonNull
    private LocalDate bookingDate;

    @NonNull
    private Double price;

    @NonNull
    private String name;

    @NonNull
    private String lastName;

    @NonNull
    private LocalDate fromDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private Integer numberOfPeople;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Long userId) {
        this.userId = userId;
    }

    @NonNull
    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(@NonNull Long destinationId) {
        this.destinationId = destinationId;
    }

    @NonNull
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(@NonNull LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    @NonNull
    public Double getPrice() {
        return price;
    }

    public void setPrice(@NonNull Double price) {
        this.price = price;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(@NonNull LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @NonNull
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }

    @NonNull
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(@NonNull Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
