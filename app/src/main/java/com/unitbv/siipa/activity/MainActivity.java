package com.unitbv.siipa.activity;

import static com.unitbv.siipa.utils.Utils.deleteDatabaseFile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.fragments.LoginFragment;
import com.unitbv.siipa.user.RoleEnum;
import com.unitbv.siipa.user.User;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        deleteDatabaseFile(getApplicationContext(), "mobile-database");
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
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc2");
            destination.setLocation("loc2");
            destination.setName("name2");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc3");
            destination.setLocation("loc3");
            destination.setName("name3");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc3");
            destination.setLocation("loc3");
            destination.setName("name3");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc4");
            destination.setLocation("loc4");
            destination.setName("name4");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc5");
            destination.setLocation("loc5");
            destination.setName("name5");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);

            destination = new Destination();
            destination.setDescription("Desc5");
            destination.setLocation("loc5");
            destination.setName("name5");
            destination.setPrice(Double.valueOf("221"));
            ApplicationRoomDatabase.getDatabase(getApplicationContext()).destinationDao().addDestinations(destination);
        }
    }
}