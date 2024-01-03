package com.unitbv.siipa.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unitbv.siipa.R;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.utils.CRUDOperations;

public class DestinationDetailsFragment extends Fragment {


    private TextView nameDetails;
    private TextView locationDetails;
    private TextView priceDetails;
    private TextView descriptionDetails;
    private Button backButton;

    public DestinationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_details, container, false);
        nameDetails = view.findViewById(R.id.name_details);
        locationDetails = view.findViewById(R.id.location_details);
        priceDetails = view.findViewById(R.id.price_details);
        descriptionDetails = view.findViewById(R.id.desc_details);

        final Bundle bundle = getArguments();
        boolean destinationReceived = bundle == null ||  bundle.get(CRUDOperations.READ.toString()) == null;
        if (destinationReceived) {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
            fragmentTransaction.commit();
        }

        Destination destination = (Destination) bundle.get(CRUDOperations.READ.toString());
        nameDetails.setText(destination.getName());
        locationDetails.setText(destination.getLocation());
        descriptionDetails.setText(destination.getDescription());
        priceDetails.setText(destination.getPrice().toString());

        backButton = view.findViewById(R.id.back_button_details);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}