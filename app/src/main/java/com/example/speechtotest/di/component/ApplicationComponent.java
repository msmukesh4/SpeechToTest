package com.example.speechtotest.di.component;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.ui.speech.SpeechActivity;
import com.example.speechtotest.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

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
