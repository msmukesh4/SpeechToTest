package com.example.speechtotest.di;

import android.arch.lifecycle.ViewModelProvider;

import com.example.speechtotest.data.remote.APIClient;
import com.example.speechtotest.data.remote.APIService;
import com.example.speechtotest.ui.base.ProjectViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = ViewModelSubComponent.class)
public class AppModule {

    @Singleton
    @Provides
    APIService getAPIService(){
        return APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS);
    }

//    @Singleton
//    @Provides
//    ViewModelProvider.Factory provideViewModelFactory(
//            ViewModelSubComponent.Builder viewModelSubComponent) {
//
//        return new ProjectViewModelFactory(viewModelSubComponent.build());
//    }



}
