package com.justin.capture.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.justin.capture.utils.KeyUtils;

public class CaptureModel<T> implements CaptureModelBase {

    private View mView;
    private String mUrl;
    private T mListener;
    private byte[] data;
    private String key;

    public CaptureModel(View mView, String mUrl, T mListener, byte[] data, String key) {
        this.mView = mView;
        this.mUrl = mUrl;
        this.mListener = mListener;
        this.data = data;
        this.key = key;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public T getmListener() {
        return mListener;
    }

    public void setmListener(T mListener) {
        this.mListener = mListener;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = KeyUtils.getMD5EncryptedKey(key);
    }


    @Override
    public Bitmap getBitmap() {
        if (getData()!=null && getData().length >0) {
            return BitmapFactory.decodeByteArray(this.data, 0, this.data.length);
        }
        return null;
    }

    @Override
    public String getJsonText() {
        try {
            String str = new String(getData(), "UTF-8");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
