package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler.DateCellAdapter;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler.DateCellDifferentCallback;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler.RecyclerViewModel;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConstraintLayout mainLayout;
    private RecyclerViewModel recyclerViewModel;

    private DateCellAdapter dateCellAdapter;

    GridLayoutManager gridLayoutManager;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
        initRecycler(view);
        initObservers(view);
    }

    private void initView(View view) {
        mainLayout = view.findViewById(R.id.recyclerMainLayout);
        recyclerView = view.findViewById(R.id.listRv);
    }

    private void initListeners(View view) {
    }

    private void initRecycler(View view) {
        dateCellAdapter = new DateCellAdapter(new DateCellDifferentCallback(), car -> {
            Toast.makeText(view.getContext(), car.getId() + "", Toast.LENGTH_SHORT).show();
        });
        gridLayoutManager = new GridLayoutManager(view.getContext(), 7, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dateCellAdapter);
    }

    private void initObservers(View view) {
        recyclerViewModel.getDateCells().observe(getViewLifecycleOwner(), dateCell -> {
            dateCellAdapter.submitList(dateCell);
        });
    }
}
