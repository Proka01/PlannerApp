package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.DBManager;
import rs.raf.projekat1.aleksa_prokic_1420rn.application.SplashActivity;

public class LoginActivity extends AppCompatActivity {

    private DBManager dbManager;
    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;
    private TextView debbugTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        dbManager = new DBManager(this);
        initView();
        initListeners();
    }

    private void initView() {
        emailET = findViewById(R.id.emailET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);
        debbugTV = findViewById(R.id.debuugLoginTV);
    }

    private void initListeners() {
        loginBtn.setOnClickListener(v -> {
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            String email = emailET.getText().toString();

            //insert user to DB
            dbManager.insertUser(username,password,email);

            //insert last active user to shared preferences
            SharedPreferences sharedPreferences = getSharedPreferences("PlannerAppSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("email", email);
            editor.apply();

            Intent intentMain= new Intent(LoginActivity.this, BottomNavigationActivity.class);
            startActivity(intentMain);
            finish();
        });
    }
}