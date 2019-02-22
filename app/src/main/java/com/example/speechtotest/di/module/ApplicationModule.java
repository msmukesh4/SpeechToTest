package com.example.speechtotest.di.module;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.local.WordsLocalDataSource;
import com.example.speechtotest.data.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.remote.APIClient;
import com.example.speechtotest.data.remote.APIService;
import com.example.speechtotest.data.remote.WordsRemoteDataSource;
import com.example.speechtotest.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    Application provideApplication(){
        return this.application;
    }

    @Provides
    Context provideContext(){
        return this.application.getApplicationContext();
    }


    @Provides
    SpeechToTextDatabase provideSpeechToTextDatabase(Context context){
        return SpeechToTextDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    APIService provideAPIService(){
        return APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS);
    }

    @Singleton
    @Provides
    WordsRepository provideWordsRepository(WordsRemoteDataSource wordsRemoteDataSource,
                                           WordsLocalDataSource wordsLocalDataSource){
        return new WordsRepository(wordsRemoteDataSource, wordsLocalDataSource);
    }

    @Singleton
    @Provides
    ViewModelProvider.NewInstanceFactory provideViewModelFactory(Application application, WordsRepository wordsRepository){
        return new ViewModelFactory(application, wordsRepository);
    }
}
