package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class RecyclerViewModeDailyPlan extends ViewModel {
    public static int counter = 0;

    private MutableLiveData<List<PlanItem>> planItems = new MutableLiveData<>();
    private ArrayList<PlanItem> planItemList = new ArrayList<>();

    public RecyclerViewModeDailyPlan() {
    }

    public MutableLiveData<List<PlanItem>> getPlanItems() {
        return planItems;
    }

    public ArrayList<PlanItem> getPlanItemList() {
        return planItemList;
    }

    public void emptyLiveDataPlanItems()
    {
        planItems = new MutableLiveData<>();
        planItemList = new ArrayList<>();
        counter = 0;
    }

    public int addPlanItem(Plan plan) {
        int id = counter++;

        PlanItem planItem = new PlanItem(id, plan);
        planItemList.add(planItem);
        ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
        planItems.setValue(listToSubmit);

        return id;
    }

//    public void removePlanItem(PlanItem planItem,Context context) {
//        Toast.makeText(context, "delete pressed", Toast.LENGTH_LONG).show();
//        //Optional<PlanItem> planItemObject = planItemList.stream().filter(planItem -> planItem.getId() == id).findFirst();
//        planItemList.remove(planItem);
//        ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
//        planItems.setValue(listToSubmit);
//
////        if (planItemObject.isPresent()) {
////            planItemList.remove(planItemObject.get());
////            ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
////            planItems.setValue(listToSubmit);
////        }
//    }
}
