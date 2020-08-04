package com.example.vehiclelicense.ui.makepayment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MakePaymentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MakePaymentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}