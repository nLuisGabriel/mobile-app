package com.unitbv.siipa.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.destinations.DestinationAdapter;
import com.unitbv.siipa.service.UserDataEnum;
import com.unitbv.siipa.user.RoleEnum;

import java.util.ArrayList;
import java.util.List;

public class DestinationFragment extends Fragment implements DestinationAdapter.SortSwitchListener {

    private Switch sortPriceSwitch;
    private DestinationAdapter destinationAdapter;

    private RecyclerView recyclerView;

    private Button addDestinationButton;

    public DestinationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        recyclerView = view.findViewById(R.id.destination_recycleView);
        List<Destination> destinationList = ApplicationRoomDatabase.getDatabase(getContext()).destinationDao().getDestinations();
        destinationAdapter = new DestinationAdapter(new ArrayList<>(destinationList));
        destinationAdapter.setSortSwitchListener(this);
        recyclerView.setAdapter(destinationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        addDestinationButton = view.findViewById(R.id.add_destination_button);

        if (RoleEnum.USER.equals(UserDataEnum.INSTANCE.getUser().getRoleEnum())) {
            addDestinationButton.setVisibility(View.GONE);
        } else {
            addDestinationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView4, new AddDestinationFragment());
                    fragmentTransaction.commit();
                }
            });
        }

        SearchView searchView = view.findViewById(R.id.search_view_des);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (destinationAdapter != null) {
                    destinationAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        sortPriceSwitch = view.findViewById(R.id.switch1);
        sortPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                destinationAdapter.sortDestinations(isChecked);
            }
        });

        return view;
    }

    @Override
    public boolean isSortAscending() {
        return sortPriceSwitch.isChecked();
    }
}