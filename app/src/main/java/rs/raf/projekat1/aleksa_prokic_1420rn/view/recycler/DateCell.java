package rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateCell {

    private int id;
    private Date date;
    private String day;
    private List<Object> dailyPlanList = new ArrayList<>();

    public DateCell(Date date, String day, int id) {
        this.date = date;
        this.day = day;
        this.id = id;
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

    public List<Object> getDailyPlanList() {
        return dailyPlanList;
    }

    public void setDailyPlanList(List<Object> dailyPlanList) {
        this.dailyPlanList = dailyPlanList;
    }
}
