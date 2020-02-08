package com.example.trafficvision.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("Bruh");
    }


    public void setText(String text) {
        mText.setValue(text);

    }

    public LiveData<String> getText() {
        return mText;
    }
}