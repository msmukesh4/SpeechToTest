package com.example.speechtotest.data.remote;

import com.example.speechtotest.data.remote.response.DictionaryAPIResponse;
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
