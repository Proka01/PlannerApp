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
        return oldItem.getDate().equals(newItem.getDate());
    }
}
