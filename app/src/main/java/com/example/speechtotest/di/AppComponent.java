package com.example.speechtotest.di;

import android.app.Application;

import com.example.speechtotest.SpeechToTextApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }

}
