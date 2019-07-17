package com.justin.capturetest.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.justin.capturetest.BuildConfig;
import com.justin.capturetest.models.Board;
import com.justin.capturetest.repositories.PinBoardDataManager;

import java.util.List;

import javax.inject.Inject;

public class PinBoardViewModel extends ViewModel {

    private static String TAG = "TAG_JUSTIN";

    private final LiveData<List<Board>> mPinBoardObservable;


    public PinBoardDataManager mPinBoardDataManager;

    @Inject
    public PinBoardViewModel(PinBoardDataManager pinBoardDataManager) {
         this.mPinBoardDataManager = pinBoardDataManager;
        mPinBoardObservable= mPinBoardDataManager.getPinBoardDetails(BuildConfig.BASE_URL);
    }

    public LiveData<List<Board>> getPinBoardObservable() {

        return mPinBoardObservable;
    }

    public void getRefreshPiBoardData(){

        if (mPinBoardDataManager != null){
            mPinBoardDataManager.onRefreshPinBoard();
            mPinBoardDataManager.callPinBoardRepository(BuildConfig.BASE_URL);
        }
    }

}
