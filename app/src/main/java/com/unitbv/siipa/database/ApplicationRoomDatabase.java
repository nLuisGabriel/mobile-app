package com.unitbv.siipa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.unitbv.siipa.booking.Booking;
import com.unitbv.siipa.booking.BookingDao;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.destinations.DestinationsDao;
import com.unitbv.siipa.reviews.Review;
import com.unitbv.siipa.reviews.ReviewDao;
import com.unitbv.siipa.user.User;
import com.unitbv.siipa.user.UserDao;
import com.unitbv.siipa.utils.DateConvertor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Destination.class, Review.class, Booking.class}, version = 1, exportSchema = false)
@TypeConverters({DateConvertor.class})

public abstract class ApplicationRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DestinationsDao destinationDao();
    public abstract ReviewDao reviewDao();
    public abstract BookingDao bookingDao();

    private static volatile ApplicationRoomDatabase applicationRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ApplicationRoomDatabase getDatabase(final Context context) {
        if (applicationRoomDatabase == null) {
            synchronized (ApplicationRoomDatabase.class) {
                if (applicationRoomDatabase == null) {
                    applicationRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    ApplicationRoomDatabase.class, "mobile-database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return applicationRoomDatabase;
    }
}
