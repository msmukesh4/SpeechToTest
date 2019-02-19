package com.example.speechtotest.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.speechtotest.di.util.ViewModelKey;
import com.example.speechtotest.ui.base.ProjectViewModelFactory;
import com.example.speechtotest.ui.home.HomeViewModel;
import com.example.speechtotest.ui.speech.SpeechViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SpeechViewModel.class)
    abstract ViewModel bindSpeechViewModel(SpeechViewModel speechViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindProjectBiewModelFactory(ProjectViewModelFactory factory);
}
