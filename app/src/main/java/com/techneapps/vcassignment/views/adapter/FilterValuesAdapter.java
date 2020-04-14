package com.techneapps.vcassignment.views.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.vcassignment.models.FilterState;
import com.techneapps.vcassignment.providers.clicklisteners.FilterValueSelectedListener;
import com.techneapps.vcassignment.views.FilterActivity;
import com.techneapps.vcassignment.views.adapter.viewholder.FilterViewHolder;
import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.SingleFilterValueItemBinding;

import java.util.ArrayList;

public class FilterValuesAdapter extends RecyclerView.Adapter<FilterViewHolder> {
    private FilterActivity filterActivity;
    private int filterType;
    private ArrayList<FilterState> items;
    private FilterValueSelectedListener filterValueSelectedListener;


    public FilterValuesAdapter(FilterActivity filterActivity, int filterType, ArrayList<FilterState> items, FilterValueSelectedListener filterValueSelectedListener) {
        this.filterActivity = filterActivity;
        this.filterType = filterType;
        this.items = items;
        this.filterValueSelectedListener = filterValueSelectedListener;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleFilterValueItemBinding singleFilterValueItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_filter_value_item,parent,false);
        return new FilterViewHolder(singleFilterValueItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int position) {
        filterViewHolder.getSingleFilterValueItemBinding().setFilterState(items.get(position));

        switch (filterType){
            case 0:{
                filterViewHolder.getSingleFilterValueItemBinding().itemValue.setChecked(filterActivity.selectedTrending.contains(items.get(position).getFilterValue()));
                break;
            }
            case 1:{
                filterViewHolder.getSingleFilterValueItemBinding().itemValue.setChecked(filterActivity.selectedHeadline.contains(items.get(position).getFilterValue()));
                break;
            }
        }

        filterViewHolder.getSingleFilterValueItemBinding().itemValue.setOnCheckedChangeListener((buttonView, isChecked) -> filterValueSelectedListener.onFilterValueSelected(filterType,isChecked,items.get(position).getFilterValue()));

      //  filterViewHolder.getSingleFilterValueItemBinding().getRoot().setOnClickListener(v -> filterOptionClickedListener.onFilterOptionClicked(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
