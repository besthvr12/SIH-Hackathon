package com.example.trafficvision.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("This is home fragment");
    }


    public void setText(String text){
        mText.setValue(text);

    }
    public String getData(){
        return "Stata";
    }

    public LiveData<String> getText() {
        return mText;
    }
}