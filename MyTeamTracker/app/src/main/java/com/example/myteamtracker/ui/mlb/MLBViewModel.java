package com.example.myteamtracker.ui.mlb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MLBViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MLBViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MLB fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}