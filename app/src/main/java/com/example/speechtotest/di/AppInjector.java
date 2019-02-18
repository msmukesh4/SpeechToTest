package com.example.speechtotest.di;

import com.example.speechtotest.SpeechToTextApplication;


public class AppInjector {

    private AppInjector(){}

    public static void init(SpeechToTextApplication application){
        DaggerAppComponent.builder().create(application)
                .build().inject(application);
    }
}
