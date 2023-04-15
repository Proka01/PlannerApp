package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments.InspectPlanFragment;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItem;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.viewpager.PlanPagerAdapter;

public class InspectPlansActivity extends AppCompatActivity {

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
    }

    private void initListeners() {

    }
}