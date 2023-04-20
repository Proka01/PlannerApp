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

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItem;

public class EditPlanActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private int importance = 0;
    private EditText timeFromET;
    private EditText timeToET;
    private EditText titleET;
    private EditText descriptionET;

    private Button lowBtn;
    private Button midBtn;
    private Button highBtn;

    private Button saveBtn;
    private Button cancelBtn;

    private PlanItem fromIntentPlanItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        fromIntentPlanItem = (PlanItem) getIntent().getSerializableExtra("planItem");
        init();
    }

    private void init()
    {
        initView();
        initListeners();
    }

    private void initView() {
        timeFromET = findViewById(R.id.editTextTimeFrom_edit_plan);
        timeToET = findViewById(R.id.editTextTimeTo_edit_plan);
        titleET = findViewById(R.id.editTextTitle_edit_plan);
        descriptionET = findViewById(R.id.description_edit_plan);
        lowBtn = findViewById(R.id.lowBtn_edit_plan);
        midBtn = findViewById(R.id.midBtn_edit_plan);
        highBtn = findViewById(R.id.highBtn_edit_plan);
        saveBtn = findViewById(R.id.savePlanBtn_edit_plan);
        cancelBtn = findViewById(R.id.cancelCreatePlaneBtn_edit_plan);
        actionBar = getSupportActionBar();

        //seting header title to date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromIntentPlanItem.getPlan().getDate());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthDayYear = monthFormat.format(new Date(0, calendar.get(Calendar.MONTH), 1));
        monthDayYear += " " + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + ". " + String.valueOf(calendar.get(Calendar.YEAR)) + ".";
        SpannableString title = new SpannableString(monthDayYear);
        title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(title);

        String time[] = fromIntentPlanItem.getPlan().getTime().split(" - ");
        if(time.length > 0)
        {
            timeFromET.setText(time[0]);
            timeToET.setText(time[1]);
        }
        importance = fromIntentPlanItem.getPlan().getImportanceColor();
        titleET.setText(fromIntentPlanItem.getPlan().getTitle());
        descriptionET.setText(fromIntentPlanItem.getPlan().getDescription());
    }

    private void initListeners() {
        saveBtn.setOnClickListener(v -> {
            String time = timeFromET.getText().toString() + " - " + timeToET.getText().toString();
            String title = titleET.getText().toString();
            String desc = descriptionET.getText().toString();
            Date date = fromIntentPlanItem.getPlan().getDate();

            //TODO onemoguciti da se doda plan ukoliko se preklpa sa satnicom drugih planova

            Plan editedPlan = new Plan(date,time,title,desc,importance);
            fromIntentPlanItem.setPlan(editedPlan);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("editedPlanItem", fromIntentPlanItem);
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
            Intent returnIntent = new Intent();
            setResult(500, returnIntent);
            finish();
        });
    }
}