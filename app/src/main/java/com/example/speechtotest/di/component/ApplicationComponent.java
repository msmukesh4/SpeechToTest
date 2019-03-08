package com.example.speechtotest.di.component;

import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.ui.speech.SpeechActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2 basic tutorial
 * <a href="https://www.youtube.com/watch?v=ZZ_qek0hGkM&list=PLrnPJCHvNZuA2ioi4soDZKz8euUQnJW65">tutorial</a>
 */
@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {


//    // view model factory to inject view model
//    void inject(ViewModelFactory viewModelFactory);

    // Activities
    void inject(HomeActivity homeActivity);
    void inject(SpeechActivity speechActivity);

//    WordsRepository getWordsRepository();

}
