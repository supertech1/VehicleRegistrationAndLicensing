package com.example.vehiclelicense.ui.v_registration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VRegistrationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VRegistrationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}