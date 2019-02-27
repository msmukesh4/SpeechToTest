package com.example.speechtotest.util;

import com.example.speechtotest.BuildConfig;

import java.util.Dictionary;

import retrofit2.http.PUT;

/**
 * Created by mukesh on 30/01/19
 */
public class Constants {

    public static final String BASE_URL = "http://a.galactio.com/interview/";

    public static final String DICTIONARY_URL = BuildConfig.BASE_URL + "dictionary-v2.json";

}
