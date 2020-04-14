package com.techneapps.vcassignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.ActivityHomeBinding;
import com.techneapps.vcassignment.models.FilterState;
import com.techneapps.vcassignment.models.ResponseItem;
import com.techneapps.vcassignment.providers.clicklisteners.OnSortingChangedListener;
import com.techneapps.vcassignment.utils.SortingHelper;
import com.techneapps.vcassignment.viewmodels.HomeViewModel;
import com.techneapps.vcassignment.viewmodels.HomeViewModelFactory;
import com.techneapps.vcassignment.views.adapter.HomeDataAdapter;
import com.techneapps.vcassignment.views.bottomsheets.SortingBSV;

import java.util.ArrayList;
import java.util.Objects;

import static com.techneapps.vcassignment.utils.SystemUtils.isInternetConnected;

@SuppressWarnings("unchecked")
public class HomeActivity extends AppCompatActivity implements OnSortingChangedListener {

    private static final int FILTER_INTENT_REQ_CODE = 1212;
    private ActivityHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private HomeDataAdapter homeDataAdapter;

    private boolean isLoading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private ArrayList<ResponseItem> responseItemsFetched = new ArrayList<>();
    private ArrayList<ResponseItem> responseItemsFilterResults = new ArrayList<>();

    private SortingBSV sortingBSV;

    private ArrayList<FilterState> trendingFilterOptions = new ArrayList<>();
    private ArrayList<FilterState> headlineFilterOptions = new ArrayList<>();

    private ArrayList<String> headlineFilterOptionsTemp = new ArrayList<>();
    private int sortingCheckedID = -1;

    private ArrayList<String> selectedTrending = new ArrayList<>();
    private ArrayList<String> selectedHeadline = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private int lastVisiblePositionOffset = -1;
    private int lastVisiblePosition = -1;

    private int currentSortingID = -1;
    private boolean scrollListenerAssigned = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setSupportActionBar(homeBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Home");

        trendingFilterOptions.add(new FilterState("Is Trending", false));

        initializeViews();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {

        initializeViewModel();
        initializeRecyclerView();

        if (isInternetConnected(this)) {
            homeBinding.noInternetConnectionContainer.setVisibility(View.GONE);
            observeDataFromServer();
            initializeTopBar();
        }else {
            homeBinding.progressBar.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.error).into(homeBinding.noInternetConnectionImage);
            homeBinding.noInternetConnectionContainer.setVisibility(View.VISIBLE);
        }
    }

    public void retryConnection(View view){
        homeBinding.noInternetConnectionContainer.setVisibility(View.GONE);
        homeBinding.progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(this::initializeViews,500);

    }


    private void initializeViewModel() {
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory()).get(HomeViewModel.class);
    }


    private void initializeRecyclerView() {
        homeBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        homeBinding.recyclerView.setLayoutManager(linearLayoutManager);

        homeBinding.progressBar.setVisibility(View.VISIBLE);
        homeDataAdapter = new HomeDataAdapter(responseItemsFilterResults);
        homeBinding.recyclerView.setAdapter(homeDataAdapter);
    }


    private void observeDataFromServer() {



        homeViewModel.getArrayListMutableLiveData().observe(this, responseItems -> {
            for (ResponseItem responseItem : responseItems) {
                responseItem.calculateRemainingDays();
                if (!headlineFilterOptionsTemp.contains(responseItem.getItemHeadline())) {
                    headlineFilterOptionsTemp.add(responseItem.getItemHeadline());
                    headlineFilterOptions.add(new FilterState(responseItem.getItemHeadline(), false));
                }
            }


            responseItemsFetched.addAll(responseItems);
            Objects.requireNonNull(getSupportActionBar()).setSubtitle(responseItemsFetched.size() + " Items fetched");

            if (homeBinding.sortFilterViewContainer.getVisibility() != View.VISIBLE) {
                homeBinding.sortFilterViewContainer.setVisibility(View.VISIBLE);
            }

            filterItems();
            homeBinding.progressBar.setVisibility(View.GONE);
            if (!scrollListenerAssigned) {
                initScrollListenerForLoadingNextPage();
            }
        });
    }


    private void initializeTopBar() {
        homeBinding.sortView.addOnPrefClickListener(this::onSortViewClicked);
        homeBinding.filterView.addOnPrefClickListener(this::onFilterViewClicked);

    }




    public void onSortViewClicked() {
        sortingBSV = SortingBSV.newInstance(sortingCheckedID);
        sortingBSV.setOnCheckedChangeListener(this);
        sortingBSV.show(getSupportFragmentManager(), "sorting_fragment");
    }


    public void onFilterViewClicked() {
        Intent filterIntent = new Intent(getApplicationContext(), FilterActivity.class);
        filterIntent.putExtra("headlines", headlineFilterOptions);
        filterIntent.putExtra("trending", trendingFilterOptions);
        filterIntent.putExtra("selectedTrending", selectedTrending);
        filterIntent.putExtra("selectedHeadline", selectedHeadline);
        startActivityForResult(filterIntent, FILTER_INTENT_REQ_CODE);
    }


    private void initScrollListenerForLoadingNextPage() {

        scrollListenerAssigned = true;
        homeBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        homeDataAdapter.getItemCount() - 1) {
                    //load more items code is here
                    if (!isLoading) {
                        loadMorePages();
                    }
                }
            }
        });
    }

    private void loadMorePages() {
       // Log.e("OnLoadMore", "OnLoadMore");
        lastVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        View firstItemView = linearLayoutManager.getChildAt(0);
        if (lastVisiblePosition > 0 && firstItemView != null) {
            lastVisiblePositionOffset = firstItemView.getTop();
        }
        homeBinding.progressBar.setVisibility(View.VISIBLE);
        isLoading = true;
        observeDataFromServer();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILTER_INTENT_REQ_CODE && resultCode == RESULT_OK) {
            assert data != null;
            lastVisiblePosition = 0;
            lastVisiblePositionOffset = 0;

            selectedTrending = (ArrayList<String>) Objects.requireNonNull(data.getExtras()).get("selectedTrending");
            selectedHeadline = (ArrayList<String>) data.getExtras().get("selectedHeadline");
            // Log.e("onActivityResult", "selectedTrending =" + selectedTrending.size());
            // Log.e("onActivityResult", "selectedHeadline =" + selectedHeadline.size());
            filterItems();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void OnSortingChanged(boolean isFromUser, int checkedId) {
       // Log.e("OnSortingChanged", "OnSortingChanged isFromUser="+isFromUser);
        currentSortingID = checkedId;

        if (sortingBSV != null && sortingBSV.isAdded()) {
            sortingBSV.dismiss();

        }
        sortingCheckedID = checkedId;
        if (checkedId == R.id.ascendingSort) {
            //Toast.makeText(this, "ascendingSort", Toast.LENGTH_SHORT).show();
            SortingHelper.sortItemsByExpiryDateAscending(responseItemsFilterResults);
           } else if (checkedId == R.id.descendingSort) {
            //Toast.makeText(this, "descendingSort", Toast.LENGTH_SHORT).show();
            SortingHelper.sortItemsByExpiryDateDescending(responseItemsFilterResults);

        }

        homeBinding.recyclerView.post(() -> homeDataAdapter.notifyDataSetChanged());

        isLoading = false;
        homeViewModel.getArrayListMutableLiveData().removeObservers(this);
        if (isFromUser){
            linearLayoutManager.scrollToPosition(0);
        }
    }

    private void filterItems() {
        responseItemsFilterResults.clear();
        if (selectedTrending.size() == 0 && selectedHeadline.size() == 0) {
            responseItemsFilterResults.addAll(responseItemsFetched);

        } else {
            if (selectedTrending.size() == 0) {
                // Log.e("filterItems", "2nd condition");
                for (int i = 0; i < responseItemsFetched.size(); i++) {
                    for (int j = 0; j < selectedHeadline.size(); j++) {
                        if (responseItemsFetched.get(i).getItemHeadline().equals(selectedHeadline.get(j))) {
                            responseItemsFilterResults.add(responseItemsFetched.get(i));
                        }
                    }
                }

            } else if (selectedHeadline.size() == 0) {
                //  Log.e("filterItems", "3rd condition");
                for (int i = 0; i < responseItemsFetched.size(); i++) {
                    if (responseItemsFetched.get(i).isIsTrending()) {
                        responseItemsFilterResults.add(responseItemsFetched.get(i));
                    }
                }
            } else {
                //  Log.e("filterItems", "4th condition");
                for (int i = 0; i < responseItemsFetched.size(); i++) {
                    for (int j = 0; j < selectedHeadline.size(); j++) {
                        if (responseItemsFetched.get(i).getItemHeadline().equals(selectedHeadline.get(j))
                                && responseItemsFetched.get(i).isIsTrending()) {
                            responseItemsFilterResults.add(responseItemsFetched.get(i));
                        }
                    }
                }
            }
        }
        OnSortingChanged(false,currentSortingID);
    }

}
