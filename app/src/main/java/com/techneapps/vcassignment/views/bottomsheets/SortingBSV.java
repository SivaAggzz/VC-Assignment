package com.techneapps.vcassignment.views.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.techneapps.vcassignment.R;
import com.techneapps.vcassignment.databinding.SortingBsvBinding;
import com.techneapps.vcassignment.providers.clicklisteners.OnSortingChangedListener;

public class SortingBSV extends BottomSheetDialogFragment {

    private OnSortingChangedListener onSortingChangedListener;

    public static SortingBSV newInstance(int checkedID) {
        SortingBSV sortingBSV = new SortingBSV();
        Bundle bundle = new Bundle();
        bundle.putInt("checkedID", checkedID);
        sortingBSV.setArguments(bundle);
        return sortingBSV;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        SortingBsvBinding sortingBsvBinding = DataBindingUtil.inflate(inflater, R.layout.sorting_bsv, container, true);

        assert getArguments() != null;
        int sortingCheckedID = getArguments().getInt("checkedID");
        if (sortingCheckedID != -1) {
            ((RadioButton) sortingBsvBinding.radioGroup.findViewById(sortingCheckedID)).setChecked(true);
        }
        RadioGroup.OnCheckedChangeListener onCheckedChangeListener = (group, checkedId) -> SortingBSV.this.onSortingChangedListener.OnSortingChanged(true,checkedId);

        sortingBsvBinding.radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        return sortingBsvBinding.getRoot();
    }

    public void setOnCheckedChangeListener(OnSortingChangedListener onSortingChangedListener) {
        this.onSortingChangedListener = onSortingChangedListener;
    }
}
