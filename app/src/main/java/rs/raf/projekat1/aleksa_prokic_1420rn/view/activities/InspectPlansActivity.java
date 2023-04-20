package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments.InspectPlanFragment;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItem;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.viewpager.PlanPagerAdapter;

public class InspectPlansActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private PlanPagerAdapter planPagerAdapter;
    private List<Fragment> planFragments;

    private DateCell fromIntentDateCell;
    private int fromIntentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_plans);
        fromIntentDateCell = (DateCell) getIntent().getSerializableExtra("dateCell");
        fromIntentIndex = getIntent().getIntExtra("index",0);
        planFragments = new ArrayList<>();
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 4 && resultCode == Activity.RESULT_OK) {
            PlanItem editedPlanItem = (PlanItem) data.getSerializableExtra("editedPlanItem");

            Intent returnIntent = new Intent();
            returnIntent.putExtra("editedPlanItem", editedPlanItem);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

        if(requestCode == 4 && resultCode == 500) {
            Intent returnIntent = new Intent();
            setResult(500, returnIntent);
            finish();
        }
    }

    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(500, returnIntent);
        finish();
    }

    private void init()
    {
        for(Plan p : fromIntentDateCell.getDailyPlanList())
            planFragments.add(InspectPlanFragment.newInstance(fromIntentDateCell,p,fromIntentIndex,this));

        initViews();
        initListeners();
    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager_for_plans);
        planPagerAdapter = new PlanPagerAdapter(getSupportFragmentManager(), planFragments);
        viewPager.setAdapter(planPagerAdapter);
        viewPager.setCurrentItem(fromIntentIndex);

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

    }
}