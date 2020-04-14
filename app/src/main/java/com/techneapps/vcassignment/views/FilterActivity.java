package com.techneapps.vcassignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.ActivityFilterBinding;
import com.techneapps.vcassignment.models.FilterState;
import com.techneapps.vcassignment.providers.clicklisteners.FilterOptionClickedListener;
import com.techneapps.vcassignment.providers.clicklisteners.FilterValueSelectedListener;
import com.techneapps.vcassignment.utils.FilterHelper;
import com.techneapps.vcassignment.views.adapter.FilterTitleAdapter;
import com.techneapps.vcassignment.views.adapter.FilterValuesAdapter;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class FilterActivity extends AppCompatActivity implements FilterOptionClickedListener, FilterValueSelectedListener {
    private ActivityFilterBinding filterBinding;
    private FilterTitleAdapter filterTitleAdapter;


    public ArrayList<String> selectedTrending = new ArrayList<>();
    public ArrayList<String> selectedHeadline = new ArrayList<>();

    private FilterValuesAdapter trendingFilterValuesAdapter;
    private FilterValuesAdapter headlinesFilterValuesAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        setSupportActionBar(filterBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Filters");

        selectedTrending = (ArrayList<String>) Objects.requireNonNull(getIntent().getExtras()).get("selectedTrending");
        selectedHeadline = (ArrayList<String>) getIntent().getExtras().get("selectedHeadline");


        filterTitleAdapter = new FilterTitleAdapter(FilterHelper.getFilterOptions(), this, this);
        filterBinding.filterTitleView.setAdapter(filterTitleAdapter);

        filterBinding.filterTitleView.post(() -> onFilterOptionClicked(0));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearFilter) {
            selectedTrending.clear();
            selectedHeadline.clear();
            if (trendingFilterValuesAdapter != null) {
                trendingFilterValuesAdapter.notifyDataSetChanged();
            }
            if (headlinesFilterValuesAdapter != null) {
                headlinesFilterValuesAdapter.notifyDataSetChanged();
            }
        }else if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFilterOptionClicked(int position) {
        filterTitleAdapter.setCurrentSelectedItem(position);
        switch (position) {
            case 0: {
                ArrayList<FilterState> trendingFilterOptions = getIntent().getParcelableArrayListExtra("trending");
                trendingFilterValuesAdapter = new FilterValuesAdapter(this, 0, trendingFilterOptions, this);
                filterBinding.filterValuesView.setAdapter(trendingFilterValuesAdapter);
                break;
            }
            case 1: {
                ArrayList<FilterState> headlineFilterOptions = getIntent().getParcelableArrayListExtra("headlines");
                headlinesFilterValuesAdapter = new FilterValuesAdapter(this, 1, headlineFilterOptions, this);
                filterBinding.filterValuesView.setAdapter(headlinesFilterValuesAdapter);
                break;
            }

        }
    }

    public void applyButtonClicked(@Nullable View view) {
        returnResult();
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("selectedTrending", selectedTrending);
        returnIntent.putExtra("selectedHeadline", selectedHeadline);

        setResult(RESULT_OK, returnIntent);
        finish();
    }


    @Override
    public void onFilterValueSelected(int filterType, boolean isChecked, String value) {
        if (filterType == 0) {
            if (!isChecked) {
                selectedTrending.remove(value);
            } else {
                selectedTrending.add(value);
            }
        } else if (filterType == 1) {
            if (!isChecked) {
                selectedHeadline.remove(value);
            } else {
                selectedHeadline.add(value);
            }
        }
       // Log.e("Filter Size", "selectedTrending =" + selectedTrending.size());
       // Log.e("Filter Size", "selectedHeadline =" + selectedHeadline.size());

    }
}
