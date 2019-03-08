package com.example.speechtotest.di.component;

import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.ui.home.HomeActivityTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mukesh on 08/03/19
 *
 * Dagger2 basic tutorial
 * <a href="https://www.youtube.com/watch?v=ZZ_qek0hGkM&list=PLrnPJCHvNZuA2ioi4soDZKz8euUQnJW65">tutorial</a>
 */
@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponentTest {

    // Activities
    void inject(HomeActivityTest homeActivity);

}