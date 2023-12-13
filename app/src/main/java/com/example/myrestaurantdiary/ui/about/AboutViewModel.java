package com.example.myrestaurantdiary.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("<b>COMP 3074</b><br/><b>PROJECT P53</b><br/><br/><i>Ryan You</i><br/><i>100876273</i><br/><i>Takami Nakashima</i><i>101395226</i><br/>");
    }

    public LiveData<String> getText() {
        return mText;
    }
}