package com.example.myteamtracker.ui.nfl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NFLViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NFLViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is NFL fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}