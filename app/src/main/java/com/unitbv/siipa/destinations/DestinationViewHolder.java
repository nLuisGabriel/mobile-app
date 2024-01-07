package com.unitbv.siipa.destinations;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.fragments.AddDestinationFragment;
import com.unitbv.siipa.fragments.CreateDialog;
import com.unitbv.siipa.fragments.DestinationDetailsFragment;
import com.unitbv.siipa.service.UserDataEnum;
import com.unitbv.siipa.user.RoleEnum;
import com.unitbv.siipa.utils.CRUDOperations;

public class DestinationViewHolder extends RecyclerView.ViewHolder{

    TextView name;
    TextView price;
    TextView location;

    Button removeButton;
    Button updateButton;
    Button detailsButton;

    DestinationAdapter destinationAdapter;

    Context context;

    public DestinationViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        name = itemView.findViewById(R.id.dest_name);
        price = itemView.findViewById(R.id.dest_price);
        location = itemView.findViewById(R.id.dest_loc);
        removeButton = itemView.findViewById(R.id.delete_dest);
        updateButton = itemView.findViewById(R.id.edit_btn);
        detailsButton = itemView.findViewById(R.id.details_btn);

        initDetailsButton();

        if (RoleEnum.USER.equals(UserDataEnum.INSTANCE.getUser().getRoleEnum())) {
            removeButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
        } else {
            initDeleteButton();
            initUpdateButton();
        }

    }

    private void initDetailsButton() {
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Bundle bundle = new Bundle();
                Destination destination = destinationAdapter.destinationArrayList.get(position);
                bundle.putSerializable(CRUDOperations.READ.toString(), destination);

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                DestinationDetailsFragment destinationFragment = new DestinationDetailsFragment();
                destinationFragment.setArguments(bundle);

                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.fragmentContainerView4, destinationFragment).commit();
            }
        });
    }

    private void initUpdateButton() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Bundle bundle = new Bundle();
                Destination destination = destinationAdapter.destinationArrayList.get(position);
                bundle.putSerializable(CRUDOperations.UPDATE.toString(), destination);

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                AddDestinationFragment destinationFragment = new AddDestinationFragment();
                destinationFragment.setArguments(bundle);

                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.fragmentContainerView4, destinationFragment).commit();
            }
        });
    }

    private void initDeleteButton() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Destination destination = destinationAdapter.destinationArrayList.get(position);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getAdapterPosition();
                        Destination destination = destinationAdapter.destinationArrayList.get(position);
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ApplicationRoomDatabase.getDatabase(v.getContext()).destinationDao().deleteDestination(destination);
                                destinationAdapter.destinationArrayList.remove(position);
                                destinationAdapter.destinationArrayListFull.remove(destination);
                                destinationAdapter.notifyItemRemoved(position);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                String message = "You want to delete: "+ destination.getName() + "?";
                CreateDialog.dialog(v.getContext(), message, "Yes", "No", dialogClickListener);
            }
        });
    }

    public DestinationViewHolder linkAdapter(DestinationAdapter destinationAdapter) {
        this.destinationAdapter = destinationAdapter;
        return this;
    }

}
