package com.example.speechtotest.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.di.ViewModelSubComponent;
import com.example.speechtotest.ui.base.ProjectViewModelFactory;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.ui.speech.SpeechActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector
    abstract SpeechActivity bindSpeechActivity();

}

//@Module(subcomponents = ViewModelSubComponent.class)
//public class ActivityBindingModule {
//
//    @Singleton
//    @Provides
//    ViewModelProvider.Factory provideViewModelFactory(
//            ViewModelSubComponent.Builder viewModelSubComponent) {
//
//        return new ProjectViewModelFactory(viewModelSubComponent.build());
//    }
//
//    @Singleton
//    @Provides
//    SpeechToTextApplication provideSpeechToTextApplication(){
//
//    }
//}
