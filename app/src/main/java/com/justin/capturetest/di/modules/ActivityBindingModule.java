package com.justin.capturetest.di.modules;


import com.justin.capturetest.activities.PinBoardActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract PinBoardActivity bindMainActivity();
}
