package com.techneapps.vcassignment.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.vcassignment.providers.clicklisteners.FilterOptionClickedListener;
import com.techneapps.vcassignment.views.adapter.viewholder.FilterViewHolder;
import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.SingleFilterItemBinding;

import java.util.ArrayList;
import java.util.Objects;

public class FilterTitleAdapter extends RecyclerView.Adapter<FilterViewHolder> {
    private ArrayList<String> items;
    private FilterOptionClickedListener filterOptionClickedListener;
    private RecyclerView recyclerView;
    private Context context;


    public FilterTitleAdapter(ArrayList<String> items, FilterOptionClickedListener filterOptionClickedListener, Context context) {
        this.items = items;
        this.filterOptionClickedListener = filterOptionClickedListener;
        this.context = context;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recyclerView = (RecyclerView) parent;
        SingleFilterItemBinding singleFilterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_filter_item, parent, false);
        return new FilterViewHolder(singleFilterItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int position) {
        filterViewHolder.getSingleFilterItemBinding().setItemTitle(items.get(position));
        filterViewHolder.getSingleFilterItemBinding().getRoot().setOnClickListener(v -> filterOptionClickedListener.onFilterOptionClicked(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setCurrentSelectedItem(int positionSelected) {
       // Log.e("Selected position",positionSelected+"");
        for (int currentItemNumber = 0; currentItemNumber < getItemCount(); currentItemNumber++) {
            FilterViewHolder filterViewHolder = (FilterViewHolder) recyclerView.findViewHolderForLayoutPosition(currentItemNumber);
           // Log.e("currentItemNumber",currentItemNumber+"");
            if (currentItemNumber == positionSelected) {
                Objects.requireNonNull(filterViewHolder).getSingleFilterItemBinding().getRoot().setBackgroundColor(context.getResources().getColor(android.R.color.white));
                filterViewHolder.getSingleFilterItemBinding().itemText.setTextColor(context.getResources().getColor(R.color.md_blue_600));
            } else {
                Objects.requireNonNull(filterViewHolder).getSingleFilterItemBinding().getRoot().setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                filterViewHolder.getSingleFilterItemBinding().itemText.setTextColor(context.getResources().getColor(R.color.md_black_1000));
            }

        }
    }
}
