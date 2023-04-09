package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.DBManager;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.User;
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
        initDataBase();
        initView();
        initListeners();
    }

    private void initDataBase()
    {
        dbManager = new DBManager(this);

        //hardcoding database
        List<User> userList = dbManager.getAllUsers();
        if(userList.size() == 0)
        {
            //insert user to DB
            dbManager.insertUser("aa","123","aa@gmail.com");
            dbManager.insertUser("student","123","student@gmail.com");
        }
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

            List<User> userList = dbManager.getAllUsers();

            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            String email = emailET.getText().toString();
            User user = new User(email, username, password);

            if(userList.contains(user))
            {
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
            }
            else
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();

        });
    }
}