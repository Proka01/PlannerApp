package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DateCell {

    private int id;
    private Date date;
    private String day;
    private List<Plan> dailyPlanList = new ArrayList<>();

    public DateCell(Date date, String day, int id) {
        this.date = date;
        this.day = day;
        this.id = id;
        generateRandomPlans();
    }

    private void generateRandomPlans()
    {
        String titles[] = {"Algebra lecture", "Dentist", "Project meeting", "Walk the dog"};
        String descriptions[] = {"Groups and Monoids", "Wash teethes", "Prepare presentation", "Throw the ball"};
        String time[] = {"15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00"};
        int importanceColors[] = {1,2,2,0};

        int min = 0; // inclusive minimum value
        int max = 4; // exclusive maximum value
        Random random = new Random();

        int show = random.nextInt(max - min) + min;

        if(show == 0)
        {
            int numberOfPlans = random.nextInt(max - min) + min;
            numberOfPlans++;
            for(int i = 0; i < numberOfPlans; i++)
            {
                int rand = random.nextInt(max - min) + min;
                Plan plan = new Plan(date,time[rand],titles[rand],descriptions[rand],importanceColors[rand]);
                this.dailyPlanList.add(plan);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Plan> getDailyPlanList() {
        return dailyPlanList;
    }

    public void setDailyPlanList(List<Plan> dailyPlanList) {
        this.dailyPlanList = dailyPlanList;
    }
}
