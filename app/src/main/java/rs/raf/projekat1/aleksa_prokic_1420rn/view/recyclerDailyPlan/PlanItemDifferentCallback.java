package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;

public class PlanItemDifferentCallback  extends DiffUtil.ItemCallback<PlanItem>{
    @Override
    public boolean areItemsTheSame(@NonNull PlanItem oldItem, @NonNull PlanItem newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull PlanItem oldItem, @NonNull PlanItem newItem) {
        //mozda ovo treba da se modifikuje
        return oldItem.getPlan().equals(newItem.getPlan())
                && oldItem.getId() == newItem.getId();
    }
}
