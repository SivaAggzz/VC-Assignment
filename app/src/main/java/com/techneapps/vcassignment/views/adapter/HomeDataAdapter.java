package com.techneapps.vcassignment.views.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.SingleRespItemBinding;
import com.techneapps.vcassignment.models.ResponseItem;
import com.techneapps.vcassignment.views.adapter.viewholder.HomeDataViewHolder;

import java.util.ArrayList;

public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataViewHolder> {
    private ArrayList<ResponseItem> responseItems;


    public HomeDataAdapter(ArrayList<ResponseItem> responseItems) {
        this.responseItems = responseItems;
    }

    @NonNull
    @Override
    public HomeDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleRespItemBinding singleRespItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_resp_item,parent,false);
        return new HomeDataViewHolder(singleRespItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeDataViewHolder homeDataViewHolder, int position) {
        homeDataViewHolder.getSingleRespItemBinding().setResponseItem(responseItems.get(position));
    }

    @Override
    public int getItemCount() {
        return responseItems.size();
    }



}
