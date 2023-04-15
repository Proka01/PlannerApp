package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;

public class InspectPlanFragment extends Fragment {

    private String time;
    private String title;
    private String description;
    private int importance;

    private int id;
    private DateCell dateCell;

    public static InspectPlanFragment newInstance(String time, String title, String description, int importance){//int id, DateCell dateCell) {
        InspectPlanFragment fragment = new InspectPlanFragment();
        fragment.time = time;
        fragment.title = title;
        fragment.description = description;
        fragment.importance = importance;
        //fragment.dateCell = dateCell;
        //fragment.id = id;

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
        });

        deleteBtn.setOnClickListener(v -> {
            //TODO implement
        });

        return rootView;
    }

}
