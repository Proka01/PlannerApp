package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class PlanItemAdapter extends ListAdapter<PlanItem, PlanItemAdapter.ViewHolder> {

    private final Consumer<PlanItem> onPlanItemClicked;

    public PlanItemAdapter(@NonNull DiffUtil.ItemCallback<PlanItem> diffCallback, Consumer<PlanItem> onPlanItemClicked) {
        super(diffCallback);
        this.onPlanItemClicked = onPlanItemClicked;
    }


    @NonNull
    @Override
    public PlanItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_layout, parent, false);
        return new PlanItemAdapter.ViewHolder(view, parent.getContext(), position -> {
            PlanItem planItem = getItem(position);
            onPlanItemClicked.accept(planItem);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull PlanItemAdapter.ViewHolder holder, int position) {
        PlanItem planItem = getItem(position);
        holder.bind(planItem);
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

        public void bind(PlanItem planItem) {
            ImageView imageView = itemView.findViewById(R.id.planImageIV);
            TextView timeTV = itemView.findViewById(R.id.time_tv);
            TextView titleTV = itemView.findViewById(R.id.title_tv);
            ImageView editPlanIV = itemView.findViewById(R.id.editPlanIV);
            ImageView deletePlanIV = itemView.findViewById(R.id.deletePlanIV);

            if(planItem != null)
            {
                timeTV.setText(planItem.getPlan().getTime());
                titleTV.setText(planItem.getPlan().getTitle());
            }

            editPlanIV.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), "edit pressed", Toast.LENGTH_SHORT).show();
            });

            deletePlanIV.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), "delete pressed", Toast.LENGTH_SHORT).show();
            });
        }

    }

}
