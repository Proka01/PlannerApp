package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar;

import androidx.annotation.Nullable;

import java.util.Date;

public class Plan {
    private Date date;
    private String time;
    private String title;
    private String description;
    private int importanceColor;

    @Override
    public boolean equals(@Nullable Object obj) {
        Plan p = (Plan) obj;

        return
                p.getDate().equals(this.date) &&
                p.getTime().equals(this.time) &&
                p.getDescription().equals(this.description) &&
                p.getTitle().equals(this.title) &&
                p.getImportanceColor() == this.importanceColor;
    }

    public Plan() {
    }

    public Plan(String time, String title, String description, int importanceColor) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.importanceColor = importanceColor;
    }

    public Plan(Date date, String time, String title, String description, int importanceColor) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.description = description;
        this.importanceColor = importanceColor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImportanceColor() {
        return importanceColor;
    }

    public void setImportanceColor(int importanceColor) {
        this.importanceColor = importanceColor;
    }
}
