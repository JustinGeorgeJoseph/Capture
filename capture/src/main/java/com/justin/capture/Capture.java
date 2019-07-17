package com.justin.capture;

import android.content.Context;
import android.view.View;

import com.justin.capture.callbacks.CaptureImageCallback;
import com.justin.capture.models.CaptureModel;

public class Capture<T> {
    private static String TAG = "TAG_JUSTIN";


    private static Capture<CaptureImageCallback> mInstance = null;

    private Context mContext = null;
    private String mUrl = null;
    private View mView = null;
    private T mListener = null;

    private Capture(Context context) {
        this.mContext = context;
    }

    private Capture() {
    }

    public static Capture with(){
        if (mInstance == null){
            mInstance = new Capture();
        }
        return mInstance;
    }


    public static Capture with(Context context){
        if (mInstance == null){
            mInstance = new Capture(context);
        }
        return mInstance;
    }

    public Capture load(String url){
        this.mUrl = url;
        return mInstance;
    }

    public Capture<CaptureImageCallback> listener(T listener){
        this.mListener = listener;
        return mInstance;
    }

    public Capture<CaptureImageCallback> into(View view) {
        this.mView = view;
        if(view == null || mUrl == null){
            throw new NullPointerException("Expected parameters missing");
        }
        return mInstance;
    }


    public void show() {
        CaptureModel captureModel = new CaptureModel(mView,mUrl,null,null, mUrl);
        create(captureModel);
    }

    public void build() {
        CaptureModel captureModel = new CaptureModel(null,mUrl,mListener,null, mUrl);
        create(captureModel);
    }

    private void create(CaptureModel captureModel){
        CaptureManager.getInstance().download(captureModel);
    }

    public void clearPinBoardCache() {
        CaptureManager.getInstance().clearCache();
    }

}
