package rs.raf.projekat1.aleksa_prokic_1420rn.view.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class PlanPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> planFragments;

    public PlanPagerAdapter(FragmentManager fm, List<Fragment> planFragments) {
        super(fm);
        this.planFragments = planFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return planFragments.get(position);
    }

    @Override
    public int getCount() {
        return planFragments.size();
    }
}
