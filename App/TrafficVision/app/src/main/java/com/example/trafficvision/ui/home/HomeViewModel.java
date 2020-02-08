package com.example.trafficvision.ui.home;

import android.support.v4.app.INotificationSideChannel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> counter ;
    private MutableLiveData<Integer> lanespeed ;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        counter = new MutableLiveData<>();

        counter.setValue(0);

        mText.setValue("Low");
    }


    public void setText(String text) {
        mText.setValue(text);

    }

    public void setCounter(int data){
        counter.setValue(data);
    }

    public void setLanespeed(int data){
        lanespeed.setValue(data);
    }


    public MutableLiveData<Integer> getCounter(){
        return counter;
    }
    public MutableLiveData<Integer> getLanespeed(){
        return lanespeed;
    }



    public LiveData<String> getText() {
        return mText;
    }
}