package com.example.speechtotest.data.remote;

import android.support.annotation.NonNull;
import android.telephony.euicc.EuiccInfo;
import android.util.Log;

import com.example.speechtotest.R;
import com.example.speechtotest.data.WordsDataSource;
import com.example.speechtotest.data.model.DictionaryWord;
import com.example.speechtotest.data.remote.response.DictionaryAPIResponse;
import com.example.speechtotest.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordsRemoteDataSource implements WordsDataSource {

    private static final String TAG = WordsRemoteDataSource.class.getSimpleName();
//    private volatile static WordsRemoteDataSource INSTANCE;
    private final APIService apiService;
    private final AppExecutors appExecutors;

    @Inject
    public WordsRemoteDataSource(@NonNull APIService apiService, @NonNull AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        this.apiService = apiService;
    }

//    public static WordsRemoteDataSource newInstance(@NonNull AppExecutors appExecutors){
//
//        if (INSTANCE == null){
//            synchronized (WordsRemoteDataSource.class){
//                if (INSTANCE == null){
//                    INSTANCE = new WordsRemoteDataSource(appExecutors);
//                }
//            }
//        }
//
//        return INSTANCE;
//    }

    /**
     * Note: {@link LoadWordsCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                apiService.getDictionaryData().enqueue(new Callback<DictionaryAPIResponse>() {
                    @Override
                    public void onResponse(Call<DictionaryAPIResponse> call, Response<DictionaryAPIResponse> response) {
                        if (response.isSuccessful()) {
                            final List<DictionaryWord> words = response.body().getDictionaryWord();
                            Log.e(TAG, "onResponse: " + words.toString());

                            if (words != null && words.size() > 0) {
                                appExecutors.mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onWordsLoaded(words);
                                    }
                                });
                            }

                        } else {
                            Log.e(TAG, "Error while fetching dictionary data: " + response.code());
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onDataNotAvailable();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<DictionaryAPIResponse> call, Throwable t) {
                        Log.e(TAG, "Error while fetching dictionary data: " + t.getMessage());
                        appExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onDataNotAvailable();
                            }
                        });
                    }
                });
            }
        };
        appExecutors.networkIO().execute(runnable);
    }

    @Override
    public void getWord(@NonNull String word, @NonNull GetWordCallback callback) {

    }


    @Override
    public void saveWord(@NonNull DictionaryWord dictionaryWord) {

    }

    @Override
    public void activateWord(@NonNull DictionaryWord dictionaryWord) {

    }

    @Override
    public void activateWord(@NonNull String word) {

    }

    @Override
    public void resetAllWords() {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteAllWords() {

    }

    @Override
    public void deleteWord(@NonNull DictionaryWord dictionaryWord) {

    }
}
