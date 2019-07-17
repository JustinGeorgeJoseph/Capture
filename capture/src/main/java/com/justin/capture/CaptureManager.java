package com.justin.capture;

import android.util.Log;
import android.widget.ImageView;

import com.justin.capture.callbacks.CaptureImageCallback;
import com.justin.capture.callbacks.CaptureJsonCallback;
import com.justin.capture.models.CaptureModel;
import com.justin.capture.sources.CacheManager;
import com.justin.capture.sources.DownloadManager;
import com.justin.capture.sources.DownloadManagerListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CaptureManager implements DownloadManagerListener, CaptureCallbackConstants {

    private static String TAG = "TAG_JUSTIN";
    private static final int MAX_PARALLEL_DOWNLOAD_COUNT = 10;

    private static CaptureManager instance;
    private CacheManager mCachingManager;
    private HashMap<String, LinkedList<CaptureModel>> mRequests = new HashMap<>();
    private Queue<CaptureModel> mRequestQueue = new LinkedList<>();


    private CaptureManager() {
        mCachingManager = CacheManager.getInstance();
    }

    public static CaptureManager getInstance() {
        if (instance == null) {
            instance = new CaptureManager();
        }
        return instance;
    }

    public void download(CaptureModel captureModel) {
        if (checkDataAvailableInCache(captureModel))
            return;
        if (mRequests.containsKey(captureModel.getKey())) {
            mRequests.get(captureModel.getKey()).add(captureModel);
            return;
        } else {
            mRequestQueue.add(captureModel);
            Log.d(TAG, "download: 1");
            manageDownload();
        }

    }

    private synchronized void manageDownload() {
        if (mRequestQueue != null ) {
            Iterator<CaptureModel> queueIterator = mRequestQueue.iterator();
            while (queueIterator.hasNext()) {
                if (mRequests.size() <= MAX_PARALLEL_DOWNLOAD_COUNT) {
                    startDownloadData(queueIterator.next());
                    queueIterator.remove();
                } else {
                    break;
                }
            }
        }
    }

    private void startDownloadData(CaptureModel captureModel) {
        LinkedList<CaptureModel> listModels = new LinkedList<>();
        listModels.add(captureModel);
        mRequests.put(captureModel.getKey(), listModels);

        //initiate download
        DownloadManager downloadManager = new DownloadManager(this, captureModel);
        downloadManager.execute(captureModel.getmUrl());
    }

    private boolean checkDataAvailableInCache(CaptureModel captureModel) {
        CaptureModel captureModels = mCachingManager.getData(captureModel.getKey());
        if (captureModels != null) {
            captureModel.setData(captureModels.getData());
            setDownloadData(captureModel);
        }
        return false;
    }


    @Override
    public void onSuccess(CaptureModel captureModel, byte[] bytes) {
        mRequests.remove(captureModel.getKey());
        Log.d(TAG, "download: success");
        manageDownload();
        captureModel.setData(bytes);
        mCachingManager.putData(captureModel.getKey(), captureModel);
        setDownloadData(captureModel);
    }

    @Override
    public void onFailed(CaptureModel captureModel, String error) {
        mRequests.remove(captureModel.getKey());
        Log.d(TAG, "download: failed");
        manageDownload();
        captureModel.setData(null);
        mCachingManager.putData(captureModel.getKey(), captureModel);
        setFailedData(captureModel);
    }

    private void setDownloadData(CaptureModel captureModel) {
        if (captureModel.getmView() != null) {
            ((ImageView) captureModel.getmView())
                    .setImageBitmap(captureModel.getBitmap());
        } else {
            switch (captureModel.getmListener().getClass().getInterfaces()[0].getSimpleName()) {
                case CAPTURE_IMAGE_CALLBACK:
                    ((CaptureImageCallback) captureModel.getmListener()).getImage(captureModel.getBitmap());
                    break;
                case CAPTURE_JSON_CALLBACK:
                    ((CaptureJsonCallback) captureModel.getmListener()).getJson(captureModel.getJsonText());
                    break;
            }

        }
    }

    private void setFailedData(CaptureModel captureModel) {
        if (captureModel.getmView() != null) {
            ((ImageView) captureModel.getmView()).setImageBitmap(captureModel.getBitmap());
            ((ImageView) captureModel.getmView()).setAdjustViewBounds(false);
        } else {
            switch (captureModel.getmListener().getClass().getInterfaces()[0].getSimpleName()) {
                case CAPTURE_IMAGE_CALLBACK:
                    ((CaptureImageCallback) captureModel.getmListener()).onFailedDownload();
                    break;
                case CAPTURE_JSON_CALLBACK:
                    ((CaptureJsonCallback) captureModel.getmListener()).onFailedDownload();
                    break;

            }
        }
    }

    public void clearCache() {
        if (mCachingManager != null) {
            mCachingManager.clearCache();
        }
    }
}
