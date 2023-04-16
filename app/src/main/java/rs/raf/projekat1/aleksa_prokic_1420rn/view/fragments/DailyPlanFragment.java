package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.InspectPlansActivity;
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
    private EditText filterEditText;
    private FloatingActionButton floatingAddPlanBtn;
    private RecyclerView recyclerView;
    private CheckBox checkBox;

    private Button lowBtn;
    private Button midBtn;
    private Button highBtn;
    private boolean buttonDispecher = true;
    private int importanceVal = 3;

    private RecyclerViewModeDailyPlan recyclerViewModeDailyPlan;

    private PlanItemAdapter planItemAdapter;

    public DailyPlanFragment() {
        super(R.layout.fragment_dailyplan);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewModeDailyPlan = new ViewModelProvider(this).get(RecyclerViewModeDailyPlan.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModelForRerendering = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        init(view);
    }

    //okine se pri vracanju iz pozvanog activitija
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Plan newPlan = (Plan) data.getSerializableExtra("newPlan");

            recyclerViewModeDailyPlan.addPlanItem(newPlan);
            planItemAdapter.submitList(recyclerViewModeDailyPlan.getPlanItems().getValue());

            //da upamti dodatu obavezu i u cellGrid-u Calendar fragmenta
            sharedViewModel.getDateCellValue().getValue().getDailyPlanList().add(newPlan);

            //da rerenderuje tu celiju u calendar fragmentu ukoliko je potrebno
            sharedViewModelForRerendering.storeDateCellValue(sharedViewModel.getDateCellValue().getValue());
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            PlanItem editedPlanItem = (PlanItem) data.getSerializableExtra("editedPlanItem");

            int id = recyclerViewModeDailyPlan.updatePlan(editedPlanItem);
            planItemAdapter.notifyItemChanged(id);

            //da upamti dodatu obavezu i u cellGrid-u Calendar fragmenta
            (sharedViewModel.getDateCellValue().getValue().getDailyPlanList()).get(id).setDate(editedPlanItem.getPlan().getDate());
            (sharedViewModel.getDateCellValue().getValue().getDailyPlanList()).get(id).setTime(editedPlanItem.getPlan().getTime());
            (sharedViewModel.getDateCellValue().getValue().getDailyPlanList()).get(id).setTitle(editedPlanItem.getPlan().getTitle());
            (sharedViewModel.getDateCellValue().getValue().getDailyPlanList()).get(id).setDescription(editedPlanItem.getPlan().getDescription());
            (sharedViewModel.getDateCellValue().getValue().getDailyPlanList()).get(id).setImportanceColor(editedPlanItem.getPlan().getImportanceColor());

            //da rerenderuje tu celiju u calendar fragmentu ukoliko je potrebno
            sharedViewModelForRerendering.storeDateCellValue(sharedViewModel.getDateCellValue().getValue());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(View view) {
        initView(view);
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initListeners(View view) {
        floatingAddPlanBtn.setOnClickListener(v -> {

            DateCell dateCell = sharedViewModel.getDateCellValue().getValue();

            Intent intent = new Intent(getActivity(), AddPlanActivity.class);
            intent.putExtra("dateCell", dateCell);
            startActivityForResult(intent, 1);
        });

        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void afterTextChanged(Editable editable) {
                List<PlanItem> list = recyclerViewModeDailyPlan.filterPlans(editable.toString(),importanceVal,checkBox.isChecked());
                planItemAdapter.notifyDataSetChanged();
                planItemAdapter.submitList(list);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                List<PlanItem> list = recyclerViewModeDailyPlan.filterPlans(filterEditText.getText().toString(),importanceVal,isChecked);
                planItemAdapter.notifyDataSetChanged();
                planItemAdapter.submitList(list);
            }
        });

        lowBtn.setOnClickListener(v -> {
            importanceVal = buttonDispecher ? 0 : 3;
            buttonDispecher = !buttonDispecher;
            List<PlanItem> list = recyclerViewModeDailyPlan.filterPlans(filterEditText.getText().toString(),importanceVal,checkBox.isChecked());
            planItemAdapter.notifyDataSetChanged();
            planItemAdapter.submitList(list);
        });

        midBtn.setOnClickListener(v -> {
            importanceVal = buttonDispecher ? 1 : 3;
            buttonDispecher = !buttonDispecher;
            List<PlanItem> list = recyclerViewModeDailyPlan.filterPlans(filterEditText.getText().toString(),importanceVal,checkBox.isChecked());
            planItemAdapter.notifyDataSetChanged();
            planItemAdapter.submitList(list);
        });

        highBtn.setOnClickListener(v -> {
            importanceVal = buttonDispecher ? 2 : 3;
            buttonDispecher = !buttonDispecher;
            List<PlanItem> list = recyclerViewModeDailyPlan.filterPlans(filterEditText.getText().toString(),importanceVal,checkBox.isChecked());
            planItemAdapter.notifyDataSetChanged();
            planItemAdapter.submitList(list);
        });
    }

    private void initRecycler(View view) {
        planItemAdapter = new PlanItemAdapter(recyclerViewModeDailyPlan,new PlanItemDifferentCallback(), planItem -> {
            Toast.makeText(view.getContext(), planItem.getId() + "", Toast.LENGTH_SHORT).show();

            //sta se desi kad se klikne na planItem
            DateCell dateCell = sharedViewModel.getDateCellValue().getValue();
            Intent intent = new Intent(getActivity(), InspectPlansActivity.class);
            intent.putExtra("dateCell", dateCell);
            intent.putExtra("index", dateCell.getDailyPlanList().indexOf(planItem.getPlan()));
            startActivityForResult(intent, 2);
        });

        planItemAdapter.setSharedViewModel(sharedViewModel);
        planItemAdapter.setSharedViewModelForRerendering(sharedViewModelForRerendering);
        planItemAdapter.setParentFragment(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(planItemAdapter);
    }

    private void initView(View view) {
        tv = view.findViewById(R.id.daily_plan_tv);
        filterEditText = view.findViewById(R.id.daily_plan_search_et);
        floatingAddPlanBtn = view.findViewById(R.id.floating_add_btn);
        checkBox = view.findViewById(R.id.checkBox);
        lowBtn = view.findViewById(R.id.lowBtn);
        midBtn = view.findViewById(R.id.midBtn);
        highBtn = view.findViewById(R.id.highBtn);
        recyclerView = view.findViewById(R.id.plan_list_rv);
    }

    private void initObservers(View view) {

        //iz calendar fragmenta u daili fragment prebacujemo cellItem preko sharedView modela
        //kad se taj dogadjaj okine, tada punimo recyclerView od dailyPlan fragment-a
        sharedViewModel.getDateCellValue().observe(getViewLifecycleOwner(), (dateCell) -> {

            Toast.makeText(view.getContext(), String.valueOf(sharedViewModel.getDateCellValue().getValue().getDailyPlanList().size()), Toast.LENGTH_SHORT).show();

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
            planItemAdapter.notifyDataSetChanged();
            checkBox.setChecked(true);

        });



//        recyclerViewModeDailyPlan.getPlanItems().observe(getViewLifecycleOwner(), planItem -> {
//            planItemAdapter.submitList(planItem);
//        });
    }

}
