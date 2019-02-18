package com.example.speechtotest.di;

import android.arch.lifecycle.ViewModelProvider;

import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.ui.base.ProjectViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = ViewModelSubComponent.class)
public class ActivityModule {

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new ProjectViewModelFactory(viewModelSubComponent.build());
    }

    @Singleton
    @Provides
    SpeechToTextApplication provideSpeechToTextApplication(){
        Se
    }
}
