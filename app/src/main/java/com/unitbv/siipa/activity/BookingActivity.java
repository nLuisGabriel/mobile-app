package com.unitbv.siipa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unitbv.siipa.R;
import com.unitbv.siipa.fragments.BookingFragment;
import com.unitbv.siipa.fragments.DestinationFragment;

public class BookingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);;
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.bookings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.tourist_destinations) {
                    startActivity(new Intent(getApplicationContext(), TouristDestinations.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else  if (id == R.id.log_out) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else  if (id == R.id.bookings) {
                    startActivity(new Intent(getApplicationContext(), BookingActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    private void loadInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2, new BookingFragment());
        fragmentTransaction.commit();
    }
}