package com.techneapps.vcassignment.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techneapps.vcassignment.models.ResponseItem;
import com.techneapps.vcassignment.repo.HomeDataModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ResponseItem>> arrayListMutableLiveData;
    private HomeDataModel homeDataModel;

    public MutableLiveData<ArrayList<ResponseItem>> getArrayListMutableLiveData() {
        if (arrayListMutableLiveData == null) {
            arrayListMutableLiveData = new MutableLiveData<>();
        }
        homeDataModel = new HomeDataModel();
        return getArrayListMutableLiveDataFromServer();
    }

    private MutableLiveData<ArrayList<ResponseItem>> getArrayListMutableLiveDataFromServer() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = Observable.fromCallable(() -> homeDataModel.getDataFromServer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items) -> arrayListMutableLiveData.setValue(items));
        compositeDisposable.add(disposable);
        return arrayListMutableLiveData;
    }

    public void clearData(){
        if (arrayListMutableLiveData != null) {
            arrayListMutableLiveData.setValue(new ArrayList<>());
        }
    }
}
