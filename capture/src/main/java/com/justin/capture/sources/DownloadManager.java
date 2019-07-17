package com.justin.capture.sources;

import android.os.AsyncTask;

import com.justin.capture.models.CaptureModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadManager extends AsyncTask<String,Void, byte[]> {

    private DownloadManagerListener managerListener;
    private CaptureModel captureModel;

    public DownloadManager(DownloadManagerListener listener,CaptureModel model) {
        this.managerListener = listener;
        this.captureModel = model;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode ==  200) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                InputStream ip = inputStream;
                byte[] response = readFully(inputStream);
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] readFully(InputStream input) throws IOException
    {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    @Override
    protected void onPostExecute(byte[] bitmap) {
        super.onPostExecute(bitmap);
        if (managerListener!=null){
            if (bitmap == null){
                managerListener.onFailed(captureModel,"Error");
            }else {
                managerListener.onSuccess(captureModel,bitmap);
            }
        }
    }


}
