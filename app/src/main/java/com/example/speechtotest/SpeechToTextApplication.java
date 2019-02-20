package com.example.speechtotest;



import android.app.Application;

import com.example.speechtotest.di.component.ApplicationComponent;
import com.example.speechtotest.di.component.DaggerApplicationComponent;
import com.example.speechtotest.di.module.ApplicationModule;

import dagger.android.DaggerApplication;

public class SpeechToTextApplication extends Application {

    static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
