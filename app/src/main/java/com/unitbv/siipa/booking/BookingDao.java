package com.unitbv.siipa.booking;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookingDao {
    @Query("SELECT * from bookings where userId = :userId")
    List<Booking> getBookingsByUserId(Long userId);

    @Insert
    void addBooking(Booking booking);

    @Delete
    void deleteBooking(Booking booking);
}