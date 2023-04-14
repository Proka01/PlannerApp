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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.BottomNavigationActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCellAdapter;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCellDifferentCallback;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.RecyclerViewModel;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.viewpager.PagerAdapter;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConstraintLayout mainLayout;
    private RecyclerViewModel recyclerViewModel;

    private DateCellAdapter dateCellAdapter;

    private GridLayoutManager gridLayoutManager;

    private SharedViewModel sharedViewModel;

    private SharedViewModel sharedViewModelForRerendering;

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
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModelForRerendering = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
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

        //u konstruktoru se prosledi sta se desi kad se klikne na datum kao callback
        dateCellAdapter = new DateCellAdapter(new DateCellDifferentCallback(), dateCell -> {
            Toast.makeText(view.getContext(), dateCell.getId() + "", Toast.LENGTH_SHORT).show();

            //TODO: Da li ovo moze nekako bolje, vise oop da bude
            //moze li ovo lepse

            sharedViewModel.storeDateCellValue(dateCell);

            BottomNavigationActivity bottomNavigationActivity = (BottomNavigationActivity) getActivity();
            bottomNavigationActivity.getViewPager().setCurrentItem(PagerAdapter.FRAGMENT_DAILY_PLAN, false);
            BottomNavigationView bottomNavigationView = ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation));
            bottomNavigationView.setSelectedItemId(R.id.navigation_2);
        });

        gridLayoutManager = new GridLayoutManager(view.getContext(), 7, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dateCellAdapter);
    }

    private void initObservers(View view) {
        recyclerViewModel.getDateCells().observe(getViewLifecycleOwner(), dateCell -> {
            dateCellAdapter.submitList(dateCell);
        });

        sharedViewModelForRerendering.getDateCellValue().observe(getViewLifecycleOwner(), (dateCell) -> {
            // Create a new list with the modified DateCell object
            List<DateCell> newList = new ArrayList<>(dateCellAdapter.getCurrentList());
            newList.set(dateCell.getId(), dateCell);
            dateCellAdapter.submitList(newList);

            Toast.makeText(view.getContext(), "Rerender!", Toast.LENGTH_SHORT).show();

        });

        }
}
