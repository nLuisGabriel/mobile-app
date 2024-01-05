package com.unitbv.siipa.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unitbv.siipa.booking.Booking;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.databinding.FragmentBookingBinding;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final List<Booking> bookings;

    private Context context;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(FragmentBookingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.destinationName.setText(
                ApplicationRoomDatabase.
                        getDatabase(context).
                        destinationDao().
                        getDestinationById(booking.getDestinationId()).
                        getName());
        LocalDate start = booking.getFromDate();
        LocalDate end = booking.getEndDate();
        String period = "Period: " + start + "-" + end + ".";
        holder.period.setText(period);
        holder.price.setText(String.valueOf(booking.getPrice()));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView period;
        public final TextView price;
        public final TextView destinationName;

        public ViewHolder(FragmentBookingBinding binding) {
            super(binding.getRoot());
            this.period = binding.period;
            this.price = binding.price;
            this.destinationName = binding.destinationName;
        }
    }
}