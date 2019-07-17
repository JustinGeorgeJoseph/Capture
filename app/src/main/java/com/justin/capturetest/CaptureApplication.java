package com.justin.capturetest;

import com.justin.capturetest.di.components.ApplicationComponent;
import com.justin.capturetest.di.components.DaggerApplicationComponent;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class CaptureApplication extends DaggerApplication {

    ApplicationComponent applicationComponent = null;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        applicationComponent = DaggerApplicationComponent.builder().application(this).build();
        applicationComponent.inject(this);
        return applicationComponent;
    }
}
