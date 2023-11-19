package com.example.myrestaurantdiary.ui.restaurantList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RestaurantListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RestaurantListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is restaurant list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}