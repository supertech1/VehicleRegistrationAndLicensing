package com.example.vehiclelicense.ui.vehiclerenewal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehicleRenewalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VehicleRenewalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}