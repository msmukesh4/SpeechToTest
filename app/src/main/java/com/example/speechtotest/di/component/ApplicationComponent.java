package com.example.speechtotest.di.component;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(ViewModelFactory viewModelFactory);

    WordsRepository getWordsRepository();

}
