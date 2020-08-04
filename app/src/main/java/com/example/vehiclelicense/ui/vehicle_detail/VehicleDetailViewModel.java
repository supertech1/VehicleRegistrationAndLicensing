package com.example.vehiclelicense.ui.vehicle_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehicleDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VehicleDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}