package com.example.speechtotest.di.module;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.source.local.WordsLocalDataSource;
import com.example.speechtotest.data.source.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.source.remote.APIClient;
import com.example.speechtotest.data.source.remote.APIService;
import com.example.speechtotest.data.source.remote.WordsRemoteDataSource;
import com.example.speechtotest.util.AppExecutors;
import com.example.speechtotest.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = { NetworkModule.class })
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

    /**
     * created a provide method over constructor @Injection for WordsRepository just to make sure
     * it is singleton
     *
     * @param wordsRemoteDataSource : From construction Injection {@link WordsRemoteDataSource#WordsRemoteDataSource(APIService, AppExecutors)}
     * @param wordsLocalDataSource : From construction Injection {@link WordsLocalDataSource#WordsLocalDataSource(AppExecutors, SpeechToTextDatabase)}
     * @return {@link WordsRepository} Object
     */
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
