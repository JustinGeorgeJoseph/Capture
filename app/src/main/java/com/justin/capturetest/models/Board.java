
package com.justin.capturetest.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.justin.capture.Capture;
import com.justin.capturetest.R;

public class Board {


    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("urls")
    @Expose
    private Urls urls;

    public User getUser() {
        return user;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Capture.with().load(imageUrl).into(view).show();
    }
}
