package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

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
//        String titles[] = {"Algebra lecture", "Dentist", "Project meeting", "Walk the dog"};
//        String descriptions[] = {"Groups and Monoids", "Wash teethes", "Prepare presentation", "Throw the ball"};
//        String time[] = {"15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00"};
//        int importanceColors[] = {0,1,2};
//
//        int min = 0; // inclusive minimum value
//        int max = 4; // exclusive maximum value
//        Random random = new Random();
//
//        int numberOfPlans = 3;
//        for(int i = 0; i < numberOfPlans; i++)
//        {
//            int rand = random.nextInt(max - min) + min;
//            Plan plan = new Plan(time[rand],titles[rand],descriptions[rand],importanceColors[rand%3]);
//            PlanItem planItem = new PlanItem(i, plan);
//            planItemList.add(planItem);
//        }
//        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
//        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
//        ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
//        planItems.setValue(listToSubmit);
    }

    public LiveData<List<PlanItem>> getPlanItems() {
        return planItems;
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

    public void removePlanItem(int id) {
        Optional<PlanItem> planItemObject = planItemList.stream().filter(planItem -> planItem.getId() == id).findFirst();
        if (planItemObject.isPresent()) {
            planItemList.remove(planItemObject.get());
            ArrayList<PlanItem> listToSubmit = new ArrayList<>(planItemList);
            planItems.setValue(listToSubmit);
        }
    }
}
