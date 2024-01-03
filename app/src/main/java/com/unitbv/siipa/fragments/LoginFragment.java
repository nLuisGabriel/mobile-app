package com.unitbv.siipa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.unitbv.siipa.R;
import com.unitbv.siipa.activity.Home;
import com.unitbv.siipa.activity.MainActivity;
import com.unitbv.siipa.database.ApplicationRoomDatabase;
import com.unitbv.siipa.service.UserDataEnum;
import com.unitbv.siipa.user.User;


public class LoginFragment extends Fragment {

    private EditText username;
    private EditText password;

    private Button button;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username = view.findViewById(R.id.editTextText);
        password = view.findViewById(R.id.editTextTextPassword);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void login() {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        User loggedUser = ApplicationRoomDatabase.getDatabase(getContext()).userDao().getUserByUsernameAndPassword(username, password);

        if (loggedUser != null) {
            UserDataEnum.INSTANCE.setUser(loggedUser);
            Log.d("LoginFragment", "Autentificare reușită pentru utilizatorul: " +  UserDataEnum.INSTANCE.getUser());
            Intent intent = new Intent(getContext(), Home.class);
            startActivity(intent);
        } else {
            Log.d("LoginFragment", "Autentificare nereușită pentru utilizatorul: " + username);
        }
    }
}