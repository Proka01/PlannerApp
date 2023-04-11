package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler.DateCell;

public class DailyPlanFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private TextView tv;

    public DailyPlanFragment() {
        super(R.layout.fragment_dailyplan);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initObservers(view);
    }

    private void initView(View view) {
        tv = view.findViewById(R.id.daily_plan_tv);
    }

    private void initObservers(View view) {
        sharedViewModel.getDateCellValue().observe(getViewLifecycleOwner(), (dateCell) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateCell.getDate());
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            String msg = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" +String.valueOf(year);

            tv.setText(msg);
        });
    }

}
