package com.covid_spy.covidspy.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.covid_spy.covidspy.CovidData;
import com.covid_spy.covidspy.CovidDataRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<HashMap<String, CovidData>> nonUSLiveData = new MutableLiveData<>();
    private MutableLiveData<HashMap<String, CovidData>> usLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        CovidDataRepository.extractCovidData(new CovidDataRepository.UpdateUICallback() {
            @Override
            public void onCompleted(HashMap<String, CovidData> nonUSData, HashMap<String, CovidData> usData) {
                getNonUSLiveData().postValue(nonUSData);
                getUSLiveData().postValue(usData);
                Log.v("OK: ", "GOT DATA");
            }
        });
    }

    public MutableLiveData<HashMap<String, CovidData>> getNonUSLiveData() {
        return nonUSLiveData;
    }

    public MutableLiveData<HashMap<String, CovidData>> getUSLiveData() {
        return usLiveData;
    }
}