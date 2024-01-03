package com.unitbv.siipa.destinations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitbv.siipa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationViewHolder> implements Filterable {

    public interface SortSwitchListener {
        boolean isSortAscending();
    }

    private SortSwitchListener sortSwitchListener;

    ArrayList<Destination> destinationArrayList;

    ArrayList<Destination> destinationArrayListFull;

    public DestinationAdapter(ArrayList<Destination> destinationArrayList) {
        this.destinationArrayList = destinationArrayList;
        this.destinationArrayListFull =  new ArrayList<>(destinationArrayList);
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

    @Override
    public Filter getFilter() {
        return exempleFilter;
    }

    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Destination> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(destinationArrayListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Destination item: destinationArrayListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            destinationArrayList.clear();
            destinationArrayList.addAll((List)results.values);
            if (sortSwitchListener != null) {
                sortDestinations(sortSwitchListener.isSortAscending());
            }
            notifyDataSetChanged();
        }
    };

    public void sortDestinations(final boolean ascending) {
        Collections.sort(destinationArrayList, new Comparator<Destination>() {
            @Override
            public int compare(Destination o1, Destination o2) {
                if (ascending) {
                    return o1.getPrice().compareTo(o2.getPrice());
                } else {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            }
        });
        notifyDataSetChanged();
    }

    public void setSortSwitchListener(SortSwitchListener listener) {
        this.sortSwitchListener = listener;
    }

}
