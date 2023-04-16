package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class AddPlanActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private int importance = 0;
    private EditText timeFromET;
    private EditText timeToET;
    private EditText titleET;
    private EditText descriptionET;

    private Button lowBtn;
    private Button midBtn;
    private Button highBtn;

    private Button createBtn;
    private Button cancelBtn;

    private DateCell fromIntentDateCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        fromIntentDateCell = (DateCell) getIntent().getSerializableExtra("dateCell");
        init();
    }

    private void init()
    {
        initView();
        initListeners();
    }

    private void initView() {
        timeFromET = findViewById(R.id.editTextTime);
        timeToET = findViewById(R.id.editTextTimeTo);
        titleET = findViewById(R.id.editTextTitle_create_plan);
        descriptionET = findViewById(R.id.description_create_plan);
        lowBtn = findViewById(R.id.lowBtn_create_plan);
        midBtn = findViewById(R.id.midBtn_create_plan);
        highBtn = findViewById(R.id.highBtn_create_plan);
        createBtn = findViewById(R.id.createPlanBtn);
        cancelBtn = findViewById(R.id.cancelCreatePlaneBtn);

        actionBar = getSupportActionBar();

        //seting header title to date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromIntentDateCell.getDate());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthDayYear = monthFormat.format(new Date(0, calendar.get(Calendar.MONTH), 1));
        monthDayYear += " " + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + ". " + String.valueOf(calendar.get(Calendar.YEAR)) + ".";
        SpannableString title = new SpannableString(monthDayYear);
        title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(title);
    }

    private void initListeners() {
        createBtn.setOnClickListener(v -> {
            String time = timeFromET.getText().toString() + " - " + timeToET.getText().toString();
            String title = titleET.getText().toString();
            String desc = descriptionET.getText().toString();
            Date date = fromIntentDateCell.getDate();

            //TODO onemoguciti da se doda plan ukoliko se preklpa sa satnicom drugih planova

            Plan newPlan = new Plan(date,time,title,desc,importance);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("newPlan", newPlan);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        lowBtn.setOnClickListener(v -> {
            importance = 0;
        });

        midBtn.setOnClickListener(v -> {
            importance = 1;
        });

        highBtn.setOnClickListener(v -> {
            importance = 2;
        });

        cancelBtn.setOnClickListener(v -> {
            //TODO implement cancel action
        });
    }
}