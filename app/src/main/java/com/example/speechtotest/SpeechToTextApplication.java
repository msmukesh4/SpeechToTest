package com.example.speechtotest;

import com.example.speechtotest.di.component.ApplicationComponent;
import com.example.speechtotest.di.module.ApplicationModule;


import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class SpeechToTextApplication extends DaggerApplication {

//    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);

        return component;
    }


}
