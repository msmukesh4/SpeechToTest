package com.example.speechtotest.di.component;

import android.app.Application;
import android.content.Context;

import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.di.module.ActivityBindingModule;
import com.example.speechtotest.di.module.ApplicationModule;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class
})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(SpeechToTextApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }

}
