package com.unitbv.siipa.activity;


import static com.unitbv.siipa.utils.Utils.deleteDatabaseFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.unitbv.siipa.R;
import com.unitbv.siipa.booking.Booking;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.fragments.LoginFragment;
import com.unitbv.siipa.reviews.Review;
import com.unitbv.siipa.user.RoleEnum;
import com.unitbv.siipa.user.User;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        deleteDatabaseFile(getApplicationContext(), "mobile-database");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
        initDB();
    }

    private void loadInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.commit();
    }

    public void initDB() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.travel);
        String base64String = bitmapToBase64(bitmap);

        List<User> userList = ApplicationRoomDatabase.getDatabase(getApplicationContext()).userDao().getUsers();
        if (userList == null || userList.isEmpty()) {
            // Admin user
            User adminUser = new User();
            adminUser.setPassword("1234");
            adminUser.setUsername("ADMIN");
            adminUser.setCreateDate(LocalDate.now());
            adminUser.setRoleEnum(RoleEnum.ADMIN);
            adminUser.setEmail("admin@gmail.com");
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).userDao().addUser(adminUser);

            // Regular user
            User regularUser = new User();
            regularUser.setPassword("1234");
            regularUser.setUsername("USER");
            regularUser.setCreateDate(LocalDate.now());
            regularUser.setRoleEnum(RoleEnum.USER);
            regularUser.setEmail("user@gmail.com");
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).userDao().addUser(regularUser);
        }

        List<Destination> destinationList = ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().getDestinations();
        if (destinationList == null || destinationList.isEmpty()) {
            // Create realistic destinations
            createRealisticDestination("City Center", "Explore the heart of the city.", "City1", 50.0, base64String);
            createRealisticDestination("Mountain Retreat", "Escape to the serene mountains.", "Mountain1", 120.0, base64String);
            createRealisticDestination("Beach Paradise", "Relax on the sandy beaches.", "Beach1", 80.0, base64String);
            // Add more destinations as needed

            // Add a dummy review
            Review review = new Review();
            review.setComment("A wonderful experience!");
            review.setCreationDate(LocalDate.now());
            review.setDestinationId(1L);
            review.setUserId(1L);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).reviewDao().addReview(review);

            // Add a dummy booking
            Booking booking = new Booking();
            booking.setBookingDate(LocalDate.now());
            booking.setLastName("Mihai");
            booking.setName("Spiridon");
            booking.setNumberOfPeople(2);
            booking.setEndDate(LocalDate.now().plusDays(5));
            booking.setFromDate(LocalDate.now().plusDays(2));
            booking.setPrice(120.0);
            booking.setDestinationId(1L);
            booking.setUserId(1L);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).bookingDao().addBooking(booking);
        }
    }

    private void createRealisticDestination(String name, String description, String location, double price, String base64Image) {
        Destination destination = new Destination();
        destination.setDescription(description);
        destination.setLocation(location);
        destination.setName(name);
        destination.setPrice(price);
        destination.setPhotoPath(base64Image);
        ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

}