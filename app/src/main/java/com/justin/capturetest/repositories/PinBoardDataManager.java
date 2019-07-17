package com.justin.capturetest.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.justin.capture.Capture;
import com.justin.capture.callbacks.CaptureJsonCallback;
import com.justin.capturetest.models.Board;

import java.lang.reflect.Type;
import java.util.List;


public class PinBoardDataManager {

    private static String TAG = "TAG_JUSTIN";
    private static PinBoardDataManager mInstance;
    private MutableLiveData<List<Board>> mBoardData ;

    public PinBoardDataManager() {
        mBoardData = new MutableLiveData<>();
    }


    public LiveData<List<Board>> getPinBoardDetails(String url) {
        callPinBoardRepository(url);
        return mBoardData;
    }

    public void callPinBoardRepository(String url) {
        Capture.with().load(url).listener(new CaptureJsonCallback() {
            @Override
            public void getJson(String string) {
                mBoardData.setValue(getDataFromResponse(string));
            }

            @Override
            public void onFailedDownload() {
                mBoardData.setValue(null);
            }
        }).build();
    }

    private List<Board> getDataFromResponse(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Board>>() {}.getType();
        return gson.fromJson(response, type);

    }

    public void onRefreshPinBoard() {
        Capture.with().clearPinBoardCache();
    }
}
