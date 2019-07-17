package com.justin.capture.callbacks;

import android.graphics.Bitmap;

public interface CaptureImageCallback {
    void getImage(Bitmap bitmap);
    void onFailedDownload();
}
