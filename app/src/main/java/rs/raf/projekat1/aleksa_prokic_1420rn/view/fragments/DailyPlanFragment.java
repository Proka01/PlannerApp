package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.AddPlanActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItem;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItemAdapter;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItemDifferentCallback;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.RecyclerViewModeDailyPlan;

public class DailyPlanFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private SharedViewModel sharedViewModelForRerendering;
    private TextView tv;
    private FloatingActionButton floatingAddPlanBtn;
    private RecyclerView recyclerView;

    private RecyclerViewModeDailyPlan recyclerViewModeDailyPlan;

    private PlanItemAdapter planItemAdapter;

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
        recyclerViewModeDailyPlan = new ViewModelProvider(this).get(RecyclerViewModeDailyPlan.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModelForRerendering = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Plan newPlan = (Plan) data.getSerializableExtra("newPlan");

            recyclerViewModeDailyPlan.addPlanItem(newPlan);
            planItemAdapter.submitList(recyclerViewModeDailyPlan.getPlanItems().getValue());

            //da upamti dodatu obavezu i u cellGrid-u Calendar fragmenta
            sharedViewModel.getDateCellValue().getValue().getDailyPlanList().add(newPlan);

            //da rerenderuje tu celiju u calendar fragmentu ukoliko jepotrebno
            sharedViewModelForRerendering.storeDateCellValue(sharedViewModel.getDateCellValue().getValue());
        }
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    private void initListeners(View view) {
        floatingAddPlanBtn.setOnClickListener(v -> {

            DateCell dateCell = sharedViewModel.getDateCellValue().getValue();

            Intent intent = new Intent(getActivity(), AddPlanActivity.class);
            intent.putExtra("dateCell", dateCell);
            startActivityForResult(intent, 1);
        });
    }

    private void initRecycler(View view) {
        planItemAdapter = new PlanItemAdapter(new PlanItemDifferentCallback(), planItem -> {
            Toast.makeText(view.getContext(), planItem.getId() + "", Toast.LENGTH_SHORT).show();

            //TODO otvoriti novi intent ka activity sa detaljima o planu
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(planItemAdapter);
    }

    private void initView(View view) {
        tv = view.findViewById(R.id.daily_plan_tv);
        floatingAddPlanBtn = view.findViewById(R.id.floating_add_btn);
        recyclerView = view.findViewById(R.id.plan_list_rv);
    }

    private void initObservers(View view) {

        //iz calendar fragmenta u daili fragment prebacujemo cellItem preko sharedView modela
        //kad se taj dogadjaj okine, tada punimo recyclerView od dailyPlan fragment-a
        sharedViewModel.getDateCellValue().observe(getViewLifecycleOwner(), (dateCell) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateCell.getDate());
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            String msg = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" +String.valueOf(year);

            tv.setText(msg);

            //dodavanje planova u recyclerView dailyPlan fragmenta
            recyclerViewModeDailyPlan.emptyLiveDataPlanItems();
            List<Plan> dailyPlanList = dateCell.getDailyPlanList();
            for (Plan plan : dailyPlanList)
            {
                recyclerViewModeDailyPlan.addPlanItem(plan);
            }
            planItemAdapter.submitList(recyclerViewModeDailyPlan.getPlanItems().getValue());

        });



//        recyclerViewModeDailyPlan.getPlanItems().observe(getViewLifecycleOwner(), planItem -> {
//            planItemAdapter.submitList(planItem);
//        });
    }

}
