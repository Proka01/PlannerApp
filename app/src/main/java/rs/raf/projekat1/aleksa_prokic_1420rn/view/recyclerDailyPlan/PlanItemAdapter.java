package rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerDailyPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
            View planItemLayout = itemView.findViewById(R.id.plan_item_layout);
            ImageView imageView = itemView.findViewById(R.id.planImageIV);
            TextView timeTV = itemView.findViewById(R.id.time_tv);
            TextView titleTV = itemView.findViewById(R.id.title_tv);
            ImageView editPlanIV = itemView.findViewById(R.id.editPlanIV);
            ImageView deletePlanIV = itemView.findViewById(R.id.deletePlanIV);
            imageView.setBackgroundColor(Color.WHITE);

            Drawable myDrawable = context.getResources().getDrawable(R.drawable.plan_item_background);
            planItemLayout.setBackground(myDrawable);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(isPlan1BeforePlan2(planItem.getPlan().getTime(),LocalTime.now()))
                {
                    planItemLayout.setBackgroundColor(Color.rgb(152,152,152));
                }
            }

            int importance = planItem.getPlan().getImportanceColor();
            int color = 0;
            if(importance == 0) color = ContextCompat.getColor(context, R.color.low);
            else if (importance == 1) color = ContextCompat.getColor(context, R.color.mid);
            else if (importance == 2) color = ContextCompat.getColor(context, R.color.high);

            imageView.setBackgroundColor(color);

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

        @RequiresApi(api = Build.VERSION_CODES.O)
        private boolean isPlan1BeforePlan2(String time1, LocalTime time2)
        {
            LocalTime t1_from = null, t1_to = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm", Locale.getDefault());
            try {
                String t1[] = time1.split(" - ");
                t1_from = LocalTime.parse(t1[0], formatter);
                t1_to = LocalTime.parse(t1[1], formatter);
            } catch (DateTimeParseException e) {
                // Handle parsing error...
            }

            int result = t1_to.compareTo(time2);
            if (result <= 0) {
                // time1 is before time2
                return true;
            } else {
                // time1 is after time2
                return false;
            }
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
