package rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;

public class DateCellAdapter extends ListAdapter<DateCell, DateCellAdapter.ViewHolder> {

    private final Consumer<DateCell> onDateCellClicked;

    public DateCellAdapter(@NonNull DiffUtil.ItemCallback<DateCell> diffCallback, Consumer<DateCell> onDateCellClicked) {
        super(diffCallback);
        this.onDateCellClicked = onDateCellClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_cell_layout, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            DateCell dateCell = getItem(position);
            onDateCellClicked.accept(dateCell);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateCell dateCell = getItem(position);
        holder.bind(dateCell);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getAdapterPosition());
                }
            });
        }

        public void bind(DateCell dateCell) {
            TextView dayOfMonthTV = itemView.findViewById(R.id.dayOfMonth);
            dayOfMonthTV.setText(dateCell.getDay());
        }

    }

}
