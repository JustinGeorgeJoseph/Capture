package com.justin.capture.sources;

import com.justin.capture.models.CaptureModel;

public interface DownloadManagerListener {
    void onSuccess(CaptureModel captureModel,byte[] bytes);
    void onFailed(CaptureModel captureModel, String error);
}
