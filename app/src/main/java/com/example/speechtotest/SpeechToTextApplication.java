package com.example.speechtotest;

import android.app.Activity;
import android.app.Application;

import com.example.speechtotest.di.AppInjector;
import com.example.speechtotest.ui.base.BaseActivity;

import javax.inject.Inject;


import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class SpeechToTextApplication extends DaggerApplication implements HasActivityInjector {

//    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
//        AndroidInjection.inject(this);
    }

    @Override
    protected AndroidInjector<SpeechToTextApplication> applicationInjector() {
        return null;
    }


}
