package com.unitbv.siipa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.DestinationAdapter;
import com.unitbv.siipa.fragments.AddDestinationFragment;
import com.unitbv.siipa.fragments.DestinationFragment;
import com.unitbv.siipa.fragments.LoginFragment;

import java.util.ArrayList;

public class TouristDestinations extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_destinations);
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.tourist_destinations);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.tourist_destinations) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void loadInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
        fragmentTransaction.commit();
    }
}