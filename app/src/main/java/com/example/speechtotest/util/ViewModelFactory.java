package com.example.speechtotest.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.ui.home.HomeViewModel;
import com.example.speechtotest.ui.speech.SpeechViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mukesh on 21/02/19
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    @Inject
    WordsRepository wordsRepository;

    private final Application application;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    public ViewModelFactory(Application application){
        this.application = application;

        SpeechToTextApplication.getApplicationComponent().inject(this);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(application, wordsRepository);
        } else if (modelClass.isAssignableFrom(SpeechViewModel.class)) {
            //noinspection unchecked
            return (T) new SpeechViewModel(application, wordsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
