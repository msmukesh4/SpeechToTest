package com.example.speechtotest.di.component;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.ui.base.BaseViewModel;

import dagger.Component;

@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(BaseViewModel baseViewModel);

    WordsRepository getWordsRepository();

}
