package com.example.speechtotest.data.api;

import android.content.Context;

import com.example.speechtotest.data.api.response.DictionaryAPIResponse;
import com.example.speechtotest.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mukesh on 30/01/19
 */
public interface APIService {

    @GET(Constants.DICTIONARY_URL)
    Call<DictionaryAPIResponse> getDictionaryData();

}
