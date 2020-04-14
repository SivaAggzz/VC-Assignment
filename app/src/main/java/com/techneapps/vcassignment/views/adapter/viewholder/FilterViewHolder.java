package com.techneapps.vcassignment.views.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.vcassignment.databinding.SingleFilterItemBinding;
import com.techneapps.vcassignment.databinding.SingleFilterValueItemBinding;

public class FilterViewHolder extends RecyclerView.ViewHolder {

    private SingleFilterItemBinding singleFilterItemBinding;
    private SingleFilterValueItemBinding singleFilterValueItemBinding;

    public FilterViewHolder(@NonNull SingleFilterItemBinding singleFilterItemBinding) {
        super(singleFilterItemBinding.getRoot());
        setSingleFilterItemBinding(singleFilterItemBinding);
    }
    public FilterViewHolder(@NonNull SingleFilterValueItemBinding singleFilterValueItemBinding) {
        super(singleFilterValueItemBinding.getRoot());
        setSingleFilterValueItemBinding(singleFilterValueItemBinding);
    }

    public SingleFilterItemBinding getSingleFilterItemBinding() {
        return singleFilterItemBinding;
    }

    private void setSingleFilterItemBinding(SingleFilterItemBinding singleFilterItemBinding) {
        this.singleFilterItemBinding = singleFilterItemBinding;
    }


    public SingleFilterValueItemBinding getSingleFilterValueItemBinding() {
        return singleFilterValueItemBinding;
    }

    private void setSingleFilterValueItemBinding(SingleFilterValueItemBinding singleFilterValueItemBinding) {
        this.singleFilterValueItemBinding = singleFilterValueItemBinding;
    }
}
