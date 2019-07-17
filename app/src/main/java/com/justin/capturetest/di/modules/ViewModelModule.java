package com.justin.capturetest.di.modules;



import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.justin.capturetest.di.utils.ViewModelFactory;
import com.justin.capturetest.di.utils.ViewModelKey;
import com.justin.capturetest.viewmodels.PinBoardViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PinBoardViewModel.class)
    abstract ViewModel bindListViewModel(PinBoardViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
