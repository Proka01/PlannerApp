package rs.raf.projekat1.aleksa_prokic_1420rn.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.DBManager;
import rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB.User;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.LoginActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 3000; // 3 seconds
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbManager = new DBManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<User> userList = dbManager.getAllUsers();
                SharedPreferences sharedPreferences = getSharedPreferences("PlannerAppSharedPref", MODE_PRIVATE);

                //second argument is return value if key is not in map
                String username = sharedPreferences.getString("username", null);
                String password = sharedPreferences.getString("password", null);
                String email = sharedPreferences.getString("email", null);

                boolean skipLogin = false;
                if(username != null && password != null && email != null)
                {
                    User user = new User(email,username,password);
                    skipLogin = userList.contains(user);
                }

                if(skipLogin)
                {
                    Intent intentMain= new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    finish();
                }
                else
                {
                    Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                }

            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

}
