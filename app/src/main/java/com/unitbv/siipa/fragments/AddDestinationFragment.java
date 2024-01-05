package com.unitbv.siipa.fragments;

import static com.unitbv.siipa.activity.MainActivity.bitmapToBase64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.unitbv.siipa.R;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.destinations.Destination;
import com.unitbv.siipa.utils.CRUDOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AddDestinationFragment extends Fragment {

    private EditText destinationName;
    private EditText destinationLocation;
    private EditText destinationDescription;
    private EditText destinationPrice;
    private Button saveButton;
    private Button cancelButton;

    private String base64photo;
    private ImageView imageView;
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
        imageView = view.findViewById(R.id.imageUploader);
        final Bundle bundle = getArguments();
        boolean isUpdate = bundle != null &&  bundle.get(CRUDOperations.UPDATE.toString()) != null;
        Long id = null;
        if (isUpdate) {
            Destination destination = (Destination) bundle.get(CRUDOperations.UPDATE.toString());
            destinationName.setText(destination.getName());
            destinationLocation.setText(destination.getLocation());
            destinationDescription.setText(destination.getDescription());
            destinationPrice.setText(destination.getPrice().toString());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.travel);
            if (destination.getPhotoPath() != null && !destination.getPhotoPath().isEmpty()) {
                bitmap = base64ToBitmap(destination.getPhotoPath());
            }
            imageView.setImageBitmap(bitmap);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

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

                if (base64photo == null || base64photo.isEmpty()) {
                    destination.setPhotoPath(bitmapToBase64(BitmapFactory.decodeResource(getResources(), R.drawable.travel)));
                } else {
                    destination.setPhotoPath(base64photo);
                }
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

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length > 0 ) {
                openImageSelector();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
                base64photo = toBase64(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.travel);
            imageView.setImageBitmap(bitmap);

            base64photo = bitmapToBase64(bitmap);
        }
    }
    public String toBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public Bitmap base64ToBitmap(String result) {
        byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}