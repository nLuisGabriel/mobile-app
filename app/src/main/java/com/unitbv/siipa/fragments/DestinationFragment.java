package com.unitbv.siipa.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.destinations.DestinationAdapter;

import java.util.ArrayList;
import java.util.List;

public class DestinationFragment extends Fragment {

    private RecyclerView recyclerView;

    private Button addDestinationButton;

    public DestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        recyclerView = view.findViewById(R.id.destination_recycleView);
        List<Destination> destinationList = ApplicationRoomDatabase.getDatabase(getContext()).destinationDao().getDestinations();
        DestinationAdapter destinationAdapter = new DestinationAdapter(new ArrayList<>(destinationList));
        recyclerView.setAdapter(destinationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        addDestinationButton = view.findViewById(R.id.add_destination_button);
        addDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView4, new AddDestinationFragment());
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}