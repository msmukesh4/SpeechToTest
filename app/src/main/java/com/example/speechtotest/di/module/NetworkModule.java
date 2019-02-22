package com.example.speechtotest.di.module;

import com.example.speechtotest.data.source.remote.APIClient;
import com.example.speechtotest.data.source.remote.APIService;
import com.example.speechtotest.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mukesh on 22/02/19
 *
 * This Module is declared as abstract because all the methods in this class are static
 * and Dagger will never be creating object of this class.gst
 * This will also prevent the class constructor from marked as deprecated from dagger
 *
 * TODO initialize the LogLevel and BaseAPI url {@link APIClient} by taking information from build config rather than {@link Constants}
 */

@Module
public abstract class NetworkModule {

    @Singleton
    @Provides
    static APIService provideAPIService(){
        return APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS);
    }

}
