package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DateCellDifferentCallback extends DiffUtil.ItemCallback<DateCell>{
    @Override
    public boolean areItemsTheSame(@NonNull DateCell oldItem, @NonNull DateCell newItem) {
        return oldItem.getId() == newItem.getId();
    }


    //mozda ce trebati da se doradi
    @Override
    public boolean areContentsTheSame(@NonNull DateCell oldItem, @NonNull DateCell newItem) {

        if(oldItem.getDailyPlanList().size() != newItem.getDailyPlanList().size()) return false;

        for(int i = 0; i < oldItem.getDailyPlanList().size(); i++)
            if(!oldItem.getDailyPlanList().get(i).equals(newItem.getDailyPlanList().get(i))) return false;

        return oldItem.getDate().equals(newItem.getDate());
    }
}
