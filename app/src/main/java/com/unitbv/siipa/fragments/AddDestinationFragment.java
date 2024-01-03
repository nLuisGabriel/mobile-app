package com.unitbv.siipa.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.utils.CRUDOperations;


public class AddDestinationFragment extends Fragment {

    private EditText destinationName;
    private EditText destinationLocation;
    private EditText destinationDescription;
    private EditText destinationPrice;
    private Button saveButton;
    private Button cancelButton;
    public AddDestinationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_destination, container, false);
        destinationName = view.findViewById(R.id.destin_name);
        destinationLocation = view.findViewById(R.id.dest_locat);
        destinationDescription = view.findViewById(R.id.dest_desc);
        destinationPrice= view.findViewById(R.id.des_price);
        cancelButton = view.findViewById(R.id.cancel_dest);
        saveButton = view.findViewById(R.id.save_dest);

        final Bundle bundle = getArguments();
        boolean isUpdate = bundle != null &&  bundle.get(CRUDOperations.UPDATE.toString()) != null;
        Long id = null;
        if (isUpdate) {
            Destination destination = (Destination) bundle.get(CRUDOperations.UPDATE.toString());
            destinationName.setText(destination.getName());
            destinationLocation.setText(destination.getLocation());
            destinationDescription.setText(destination.getDescription());
            destinationPrice.setText(destination.getPrice().toString());
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
                fragmentTransaction.commit();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Destination destination = new Destination();
                destination.setPrice(Double.valueOf(destinationPrice.getText().toString()));
                destination.setName(destinationName.getText().toString());
                destination.setDescription(destinationDescription.getText().toString());
                destination.setLocation(destinationLocation.getText().toString());
                if (isUpdate) {
                    destination.setId(((Destination) bundle.get(CRUDOperations.UPDATE.toString())).getId());
                    ApplicationRoomDatabase.getDatabase(getContext()).destinationDao().updateDestination(destination);
                } else {
                    ApplicationRoomDatabase.getDatabase(getContext()).destinationDao().addDestinations(destination);
                }
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}