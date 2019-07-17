package com.justin.capturetest.di.components;

import android.app.Application;

import com.justin.capturetest.di.modules.ActivityBindingModule;
import com.justin.capturetest.di.modules.ApplicationModule;
import com.justin.capturetest.di.modules.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {ContextModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(ApplicationComponent application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();

    }

}
