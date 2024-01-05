package com.unitbv.siipa.destinations;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DestinationsDao {
    @Query("SELECT * from destination ORDER By name Asc")
    List<Destination> getDestinations();

    @Insert
    void addDestinations(Destination destination);

    @Delete
    void deleteDestination(Destination destination);

    @Update
    void updateDestination(Destination destination);

    @Query("SELECT * from destination WHERE id = :id")
    Destination getDestinationById(Long id);
}
