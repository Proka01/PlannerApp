package rs.raf.projekat1.aleksa_prokic_1420rn.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments.InspectPlanFragment;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
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

    private void init()
    {
        for(Plan p : fromIntentDateCell.getDailyPlanList())
            planFragments.add(InspectPlanFragment.newInstance(p.getTime(),p.getTitle(),p.getDescription(),p.getImportanceColor()));

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