package com.techneapps.vcassignment.views.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.vcassignment.databinding.SingleRespItemBinding;

public class HomeDataViewHolder extends RecyclerView.ViewHolder {

    private SingleRespItemBinding singleRespItemBinding;

    public HomeDataViewHolder(@NonNull SingleRespItemBinding singleRespItemBinding) {
        super(singleRespItemBinding.getRoot());
        setSingleRespItemBinding(singleRespItemBinding);
    }

    public SingleRespItemBinding getSingleRespItemBinding() {
        return singleRespItemBinding;
    }

    private void setSingleRespItemBinding(SingleRespItemBinding singleRespItemBinding) {
        this.singleRespItemBinding = singleRespItemBinding;
    }
}
