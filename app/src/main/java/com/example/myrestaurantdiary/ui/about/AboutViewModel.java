package com.example.myrestaurantdiary.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("<b>COMP 3074</b><br/><br/><i>Ryan You</i><br/><i>Takami Nakashima</i>");
    }

    public LiveData<String> getText() {
        return mText;
    }
}