package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.DBManager;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.BottomNavigationActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.LoginActivity;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }


    private DBManager dbManager;
    private TextView emailInfo;
    private TextView usernameInfo;
    private EditText passwordInfoET;
    private Button changePasswordBtn;
    private Button logoutBtn;

    private String username;
    private String password;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        dbManager = new DBManager(view.getContext());
        emailInfo = view.findViewById(R.id.emailInfoTV);
        usernameInfo = view.findViewById(R.id.usernameInfoTV);
        passwordInfoET = view.findViewById(R.id.passwordInfoET);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PlannerAppSharedPref", getActivity().MODE_PRIVATE);

        //second argument is return value if key is not in map
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);
        email = sharedPreferences.getString("email", null);

        emailInfo.setText(email);
        usernameInfo.setText(username);
        passwordInfoET.setText(password);
    }

    private void initListeners(View view) {
        logoutBtn.setOnClickListener(v -> {
            //insert last active user to shared preferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PlannerAppSharedPref", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", null);
            editor.putString("password", null);
            editor.putString("email", null);
            editor.apply();

            Intent intent= new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        changePasswordBtn.setOnClickListener(v -> {
            password = passwordInfoET.getText().toString().trim();
            dbManager.updateUser(username,password,email);
        });
    }
}
