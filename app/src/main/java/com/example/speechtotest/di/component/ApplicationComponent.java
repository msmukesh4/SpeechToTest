package com.example.speechtotest.di.component;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.di.module.ApplicationModule;
import com.example.speechtotest.ui.base.BaseViewModel;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.ui.home.HomeViewModel;

import dagger.Component;

@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(HomeViewModel baseViewModel);

    WordsRepository getWordsRepository();

}
