package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.function.Consumer;

import rs.raf.projekat1.aleksa_prokic_1420rn.R;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.AddPlanActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.activities.EditPlanActivity;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments.SharedViewModel;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;
import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.Plan;

public class PlanItemAdapter extends ListAdapter<PlanItem, PlanItemAdapter.ViewHolder> {

    private final Consumer<PlanItem> onPlanItemClicked;
    private  RecyclerViewModeDailyPlan recyclerViewModeDailyPlan;
    private SharedViewModel sharedViewModel;
    private SharedViewModel sharedViewModelForRerendering;

    private Fragment parentFragment;

    public PlanItemAdapter(RecyclerViewModeDailyPlan recyclerViewModeDailyPlan, @NonNull DiffUtil.ItemCallback<PlanItem> diffCallback, Consumer<PlanItem> onPlanItemClicked) {
        super(diffCallback);
        this.onPlanItemClicked = onPlanItemClicked;
        this.recyclerViewModeDailyPlan = recyclerViewModeDailyPlan;
    }

    public void setSharedViewModel(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }

    public void setSharedViewModelForRerendering(SharedViewModel sharedViewModelForRerendering) {
        this.sharedViewModelForRerendering = sharedViewModelForRerendering;
    }

    public void setParentFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public PlanItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_layout, parent, false);
        return new PlanItemAdapter.ViewHolder(recyclerViewModeDailyPlan,view, parent.getContext(), position -> {
            PlanItem planItem = getItem(position);
            onPlanItemClicked.accept(planItem);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull PlanItemAdapter.ViewHolder holder, int position) {
        PlanItem planItem = getItem(position);
        holder.bind(planItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private RecyclerViewModeDailyPlan recyclerViewModeDailyPlan;

        public ViewHolder(RecyclerViewModeDailyPlan recyclerViewModeDailyPlan,@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            this.recyclerViewModeDailyPlan = recyclerViewModeDailyPlan;
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
                Intent intent = new Intent(context, EditPlanActivity.class);
                intent.putExtra("planItem", planItem);
                parentFragment.startActivityForResult(intent, 2);
            });

            deletePlanIV.setOnClickListener(v -> {
                //Toast.makeText(itemView.getContext(), "delete pressed", Toast.LENGTH_SHORT).show();
                showAddSnackBar(planItem);
            });
        }

        private void showAddSnackBar(PlanItem planItem) {
            ImageView delBtn = itemView.findViewById(R.id.deletePlanIV);
            View delBtnParentLayout = (View) delBtn.getParent();

            Snackbar
                    .make(delBtnParentLayout, "Delete this plan ?", Snackbar.LENGTH_SHORT)
                    .setAction("Yes", view -> {

                        recyclerViewModeDailyPlan.getPlanItemList().remove(planItem);
                        ArrayList<PlanItem> listToSubmit = new ArrayList<>(recyclerViewModeDailyPlan.getPlanItemList());
                        recyclerViewModeDailyPlan.getPlanItems().setValue(listToSubmit);
                        PlanItemAdapter.this.submitList(listToSubmit);

                        //da upamti dodatu obavezu i u cellGrid-u Calendar fragmenta
                        sharedViewModel.getDateCellValue().getValue().getDailyPlanList().remove(planItem.getPlan());

                        //da rerenderuje tu celiju u calendar fragmentu ukoliko je potrebno
                        sharedViewModelForRerendering.storeDateCellValue(sharedViewModel.getDateCellValue().getValue());
                    })
                    .show();
        }

    }

}
