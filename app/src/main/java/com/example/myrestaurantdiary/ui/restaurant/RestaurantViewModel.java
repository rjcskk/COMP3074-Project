package com.example.myrestaurantdiary.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RestaurantViewModel extends ViewModel {


    private final MutableLiveData<Float> mRating;

    public RestaurantViewModel() {
        mRating = new MutableLiveData<>();
    }

    public LiveData<Float> getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating.setValue(rating);
    }
}