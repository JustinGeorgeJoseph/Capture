package com.justin.capturetest.di.modules;

import com.justin.capturetest.repositories.PinBoardDataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Singleton
    @Provides
    PinBoardDataManager getPinBoardDataManager(){
        return new PinBoardDataManager();
    }

}
