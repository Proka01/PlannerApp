package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class RecyclerViewModeDailyPlan extends ViewModel {
    public static int counter = 0;

    private MutableLiveData<List<PlanItem>> planItems = new MutableLiveData<>();
    private ArrayList<PlanItem> planItemList = new ArrayList<>();
    private List<PlanItem> filteredPlanItemList = new ArrayList<>(planItemList);
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
        int id = counter;

        PlanItem planItem = new PlanItem(id, plan);
        planItemList.add(planItem);
        ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
        planItems.setValue(listToSubmit);
        counter++;

        return id;
    }

    public int removePlanItem(PlanItem planItem)
    {
        return 0;
    }

    public int updatePlan(PlanItem planItem) {
        int id = planItem.getId();
        planItemList.get(id).setPlan(planItem.getPlan());
        ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
        planItems.setValue(listToSubmit);

        return id;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<PlanItem> filterPlans(String filterTitle, int importance, boolean showPastPlans) {
        filteredPlanItemList.clear();
        LocalTime currentTime = LocalTime.now();

        if((importance >= 0 && importance < 3) && !showPastPlans)
        {
            filteredPlanItemList = planItemList.stream().filter(planItem
                    -> planItem.getPlan().getImportanceColor() == importance &&
                    planItem.getPlan().getTitle().toLowerCase().startsWith(filterTitle.toLowerCase()) &&
                    !isPlan1BeforePlan2(planItem.getPlan().getTime(),currentTime)).collect(Collectors.toList());
        }
        else if(importance >= 3 && !showPastPlans)
        {
            filteredPlanItemList = planItemList.stream().filter(planItem
                    -> planItem.getPlan().getTitle().toLowerCase().startsWith(filterTitle.toLowerCase()) &&
                    !isPlan1BeforePlan2(planItem.getPlan().getTime(),currentTime)).collect(Collectors.toList());
        }
        else if((importance >= 0 && importance < 3) && showPastPlans)
        {
            filteredPlanItemList = planItemList.stream().filter(planItem
                    -> planItem.getPlan().getImportanceColor() == importance &&
                    planItem.getPlan().getTitle().toLowerCase().startsWith(filterTitle.toLowerCase())).collect(Collectors.toList());
        }
        else
        {
            filteredPlanItemList = planItemList.stream().filter(planItem
                    -> planItem.getPlan().getTitle().toLowerCase().startsWith(filterTitle.toLowerCase())).collect(Collectors.toList());
        }

        planItems.setValue(filteredPlanItemList);
        return filteredPlanItemList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isPlan1BeforePlan2(String time1, LocalTime time2)
    {
        LocalTime t1_from = null, t1_to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm", Locale.getDefault());
        try {
            String t1[] = time1.split(" - ");
            t1_from = LocalTime.parse(t1[0], formatter);
            t1_to = LocalTime.parse(t1[1], formatter);
        } catch (DateTimeParseException e) {
            // Handle parsing error...
        }

        int result = t1_to.compareTo(time2);
        if (result <= 0) {
            // time1 is before time2
            return true;
        } else {
            // time1 is after time2
            return false;
        }
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
