package rs.raf.projekat1.aleksa_prokic_1420rn.view;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class CustomComparator implements Comparator<Plan> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Plan plan1, Plan plan2) {

        String t1[] = plan1.getTime().split(" - ");
        String t2[] = plan2.getTime().split(" - ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm", Locale.getDefault());
        LocalTime timeFrom1 = LocalTime.parse(t1[0], formatter);
        LocalTime timeFrom2 = LocalTime.parse(t2[0], formatter);
        int compareTimeFrom = timeFrom1.compareTo(timeFrom2);
        if (compareTimeFrom != 0) {
            return compareTimeFrom;
        } else {
            LocalTime timeTo1 = LocalTime.parse(t1[1], formatter);
            LocalTime timeTo2 = LocalTime.parse(t2[1], formatter);
            return timeTo1.compareTo(timeTo2);
        }
    }
}
