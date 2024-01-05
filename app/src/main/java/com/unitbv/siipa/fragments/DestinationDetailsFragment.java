package com.unitbv.siipa.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unitbv.siipa.R;
import com.unitbv.siipa.booking.Booking;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.reviews.Review;
import com.unitbv.siipa.reviews.ReviewAdapter;
import com.unitbv.siipa.service.UserDataEnum;
import com.unitbv.siipa.utils.CRUDOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DestinationDetailsFragment extends Fragment {

    private TextView nameDetails;
    private TextView locationDetails;
    private TextView priceDetails;
    private TextView descriptionDetails;
    private Button backButton;
    private RecyclerView recyclerView;
    private Button addReviewButton;
    private Button booking;
    private LocalDate startDate;
    private LocalDate endDate;
    private double calculatedPrice;

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
        boolean destinationReceived = bundle == null || bundle.get(CRUDOperations.READ.toString()) == null;
        if (destinationReceived) {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
            fragmentTransaction.commit();
        }

        final Destination destination = (Destination) bundle.get(CRUDOperations.READ.toString());
        nameDetails.setText(destination.getName());
        locationDetails.setText(destination.getLocation());
        descriptionDetails.setText(destination.getDescription());
        priceDetails.setText(destination.getPrice().toString());

        TextView textViewReviewTitle = view.findViewById(R.id.textViewReviewTitle);
        textViewReviewTitle.setText("Reviews");

        recyclerView = view.findViewById(R.id.recyclerViewReviews);

        List<Review> reviews = ApplicationRoomDatabase.getDatabase(getContext()).reviewDao().
                getReviewsByDestinationId(destination.getId());
        ReviewAdapter reviewAdapter = new ReviewAdapter(new ArrayList<>(reviews));
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        backButton = view.findViewById(R.id.back_button_details);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView4, new DestinationFragment());
                fragmentTransaction.commit();
            }
        });

        addReviewButton = view.findViewById(R.id.buttonAddReview);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReviewDialog(destination, reviewAdapter);
            }
        });

        booking = view.findViewById(R.id.buttonAddBooking);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBookingDialog(destination);
            }
        });


        return view;
    }

    private void showAddReviewDialog(Destination destination, ReviewAdapter reviewAdapter) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_review, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        final EditText editTextReview = dialogView.findViewById(R.id.editTextReview);
        Button buttonSubmitReview = dialogView.findViewById(R.id.buttonSubmitReview);

        buttonSubmitReview.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String reviewText = editTextReview.getText().toString().trim();

                if (!TextUtils.isEmpty(reviewText)) {
                    Review review = new Review();
                    review.setUserId(UserDataEnum.INSTANCE.getUser().getId());
                    review.setDestinationId(destination.getId());
                    review.setComment(reviewText);
                    review.setCreationDate(LocalDate.now());
                    ApplicationRoomDatabase.getDatabase(getContext()).reviewDao().addReview(review);

                    List<Review> reviewList = reviewAdapter.getReviews();
                    reviewList.add(review);

                    reviewAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please enter a valid review", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void showAddBookingDialog(Destination destination) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_booking, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextLastName = dialogView.findViewById(R.id.editTextLastName);
        EditText editTextNumPersons = dialogView.findViewById(R.id.editTextNumPersons);
        TextView textViewCalculatedPrice = dialogView.findViewById(R.id.textViewCalculatedPrice);
        textViewCalculatedPrice.setText(String.format(Locale.getDefault(),
                "Calculated Price: $%.2f", destination.getPrice()));
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        Button buttonBookNow = dialogView.findViewById(R.id.buttonBookNow);

        ImageView openStartDatePickerButton = dialogView.findViewById(R.id.calendarIconStart);
        openStartDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDatePicker(dialogView);
            }
        });

        ImageView openEndDatePickerButton = dialogView.findViewById(R.id.calendarIconEnd);
        openEndDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEndDatePicker(dialogView);
            }
        });
        editTextNumPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCalculatedPrice(destination.getPrice(), dialogView);
            }

        });
        buttonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endDate.isBefore(startDate)) {
                    Toast.makeText(getContext(),
                            "End date cannot be before the start date", Toast.LENGTH_SHORT).show();
                } else {
                    Booking booking = new Booking();
                    booking.setBookingDate(LocalDate.now());
                    booking.setFromDate(startDate);
                    booking.setEndDate(endDate);
                    booking.setPrice(calculatedPrice);
                    booking.setName(editTextName.getText().toString());
                    booking.setLastName(editTextLastName.getText().toString());
                    booking.setDestinationId(destination.getId());
                    booking.setNumberOfPeople(Integer.parseInt(editTextNumPersons.getText().toString()));
                    booking.setUserId(UserDataEnum.INSTANCE.getUser().getId());
                    ApplicationRoomDatabase.getDatabase(getContext()).bookingDao().addBooking(booking);

                    dialog.dismiss();
                }

                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void openStartDatePicker(View dialogView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        String selectedStartDate = String.format(
                                Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                        TextView textViewSelectedStartDate = dialogView.findViewById(R.id.textViewStart);
                        textViewSelectedStartDate.setText(selectedStartDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    public void openEndDatePicker(View dialogView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        String selectedStartDate = String.format(
                                Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                        TextView textViewSelectedEndDate = dialogView.findViewById(R.id.textViewEnd);
                        textViewSelectedEndDate.setText(selectedStartDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void updateCalculatedPrice(Double destinationPrice, View dialogView) {
        EditText editTextNumPersons = dialogView.findViewById(R.id.editTextNumPersons);
        TextView textViewCalculatedPrice = dialogView.findViewById(R.id.textViewCalculatedPrice);

        try {
            int numPersons = Integer.parseInt(editTextNumPersons.getText().toString());
            double price = destinationPrice;

            calculatedPrice = numPersons * price;
            textViewCalculatedPrice.setText(
                    String.format(Locale.getDefault(), "Calculated Price: $%.2f", calculatedPrice));
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            textViewCalculatedPrice.setText("Calculated Price: N/A");
        }
    }
}