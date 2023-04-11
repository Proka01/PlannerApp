package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class PlanItem {
    private int id;
    private Plan plan;

    public PlanItem() {
    }

    public PlanItem(int id, Plan plan) {
        this.id = id;
        this.plan = plan;
    }

    public PlanItem(Plan plan) {
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
