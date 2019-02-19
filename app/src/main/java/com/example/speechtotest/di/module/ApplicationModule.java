package com.example.speechtotest.di.module;

import android.app.Application;
import android.content.Context;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.local.WordsLocalDataSource;
import com.example.speechtotest.data.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.remote.APIClient;
import com.example.speechtotest.data.remote.APIService;
import com.example.speechtotest.data.remote.WordsRemoteDataSource;
import com.example.speechtotest.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Context providesApplication(){
        return application;
    }

    @Singleton
    @Provides
    APIService provideAPIService(){
        return APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS);
    }

    @Singleton
    @Provides
    WordsRepository provideWordsRepository(){
        SpeechToTextDatabase db = SpeechToTextDatabase.getInstance(application);
        AppExecutors appExecutors = new AppExecutors();
        WordsLocalDataSource wordsLocalDataSource = WordsLocalDataSource.newInstance(appExecutors, db.dictionaryWordDAO());
        WordsRemoteDataSource wordsRemoteDataSource = WordsRemoteDataSource.newInstance(appExecutors);
        WordsRepository wordsRepository = WordsRepository.getInstance(wordsRemoteDataSource, wordsLocalDataSource);
        return wordsRepository;
    }

}

//@Module(subcomponents = ViewModelSubComponent.class)
//public class ApplicationModule {
//
//    @Singleton
//    @Provides
//    APIService provideAPIService(){
//        return APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS);
//    }
//
//    @Singleton
//    @Provides
//    WordsRepository provideWordsRepository(){
//
//        return WordsRepository.getInstance()
//    }
//
////    @Singleton
////    @Provides
////    ViewModelProvider.Factory provideViewModelFactory(
////            ViewModelSubComponent.Builder viewModelSubComponent) {
////
////        return new ProjectViewModelFactory(viewModelSubComponent.build());
////    }
//}
