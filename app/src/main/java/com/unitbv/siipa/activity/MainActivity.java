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
            User user = new User();
            user.setPassword("1234");
            user.setUsername("ADMIN");
            user.setCreateDate(LocalDate.now());
            user.setRoleEnum(RoleEnum.ADMIN);
            user.setEmail("admin@gmail.com");
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).userDao().addUser(user);
            user = new User();
            user.setPassword("1234");
            user.setUsername("USER");
            user.setCreateDate(LocalDate.now());
            user.setRoleEnum(RoleEnum.USER);
            user.setEmail("user@gmail.com");
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).userDao().addUser(user);
        }
        List<Destination> destinationList = ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().getDestinations();
        if (destinationList == null || destinationList.isEmpty()) {
            Destination destination = new Destination();
            destination.setDescription("Desc");
            destination.setLocation("loc");
            destination.setName("name");
            destination.setPrice(Double.valueOf("22"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc2");
            destination.setLocation("loc2");
            destination.setName("name2");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc3");
            destination.setLocation("loc3");
            destination.setName("name3");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc3");
            destination.setLocation("loc3");
            destination.setName("name3");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc4");
            destination.setLocation("loc4");
            destination.setName("name4");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc5");
            destination.setLocation("loc5");
            destination.setName("name5");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc5");
            destination.setLocation("loc5");
            destination.setName("name5");
            destination.setPrice(Double.valueOf("221"));
            destination.setPhotoPath(base64String);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            // add dummy review
            Review review = new Review();
            review.setComment("Dummy comment");
            review.setCreationDate(LocalDate.now());
            review.setDestinationId(1L);
            review.setUserId(1L);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).reviewDao().addReview(review);

            //add dummy use booking
            Booking booking = new Booking();
            booking.setBookingDate(LocalDate.now());
            booking.setLastName("ADMIN");
            booking.setName("ADMIN");
            booking.setNumberOfPeople(1);
            booking.setEndDate(LocalDate.MAX);
            booking.setFromDate(LocalDate.MIN);
            booking.setPrice(22.00);
            booking.setDestinationId(1L);
            booking.setUserId(1L);
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).bookingDao().addBooking(booking);
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

}