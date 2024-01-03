package com.unitbv.siipa.destinations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitbv.siipa.R;

import java.util.ArrayList;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationViewHolder> {

    ArrayList<Destination> destinationArrayList;

    public DestinationAdapter(ArrayList<Destination> destinationArrayList) {
        this.destinationArrayList = destinationArrayList;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_item_view, parent, false);
        return new DestinationViewHolder(view, parent.getContext()).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        holder.price.setText(destinationArrayList.get(position).getPrice().toString());
        holder.name.setText(destinationArrayList.get(position).getName());
        holder.location.setText(destinationArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return destinationArrayList.size();
    }
}
