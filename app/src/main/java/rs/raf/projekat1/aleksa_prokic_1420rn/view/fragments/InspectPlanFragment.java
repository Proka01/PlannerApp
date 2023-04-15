package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.EditPlanActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan.PlanItem;

public class InspectPlanFragment extends Fragment {

    private String time;
    private String title;
    private String description;
    private int importance;
    private Activity parentActivity;

    private int id;
    private DateCell dateCell;
    private Plan plan;

    public static InspectPlanFragment newInstance(DateCell dateCell, Plan plan, int id, Activity parentActivity){//int id, DateCell dateCell) {
        InspectPlanFragment fragment = new InspectPlanFragment();
        fragment.dateCell = dateCell;
        fragment.plan = plan;
        fragment.id = id;
        fragment.parentActivity = parentActivity;

        fragment.time = plan.getTime();
        fragment.title = plan.getTitle();
        fragment.description = plan.getDescription();
        fragment.importance = plan.getImportanceColor();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plan_details, container, false);

        EditText titleET = rootView.findViewById(R.id.editTextTitle_inspect_plan);
        EditText timeFromET = rootView.findViewById(R.id.editTextTimeFrom_inspect_plan);
        EditText timeToET = rootView.findViewById(R.id.editTextTimeTo_inspect_plan);
        EditText descriptionET = rootView.findViewById(R.id.description_inspect_plan);
        Button editBtn = rootView.findViewById(R.id.editPlanBtn_inspect_plan);
        Button deleteBtn = rootView.findViewById(R.id.deletePlaneBtn_inspect_plan);

        titleET.setText(title);
        String fromAndTo[] = time.split(" - ");
        if(fromAndTo.length > 0)
        {
            timeFromET.setText(fromAndTo[0]);
            timeToET.setText(fromAndTo[1]);
        }
        descriptionET.setText(description);

        editBtn.setOnClickListener(v -> {
            //TODO implement
            Intent intent = new Intent(getContext(), EditPlanActivity.class);
            PlanItem planItem = new PlanItem(id,plan);
            intent.putExtra("planItem", planItem);
            getActivity().startActivityForResult(intent, 4);
        });

        deleteBtn.setOnClickListener(v -> {
            //TODO implement
        });

        return rootView;
    }



}
