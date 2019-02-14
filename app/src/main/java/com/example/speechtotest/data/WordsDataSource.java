package com.example.speechtotest.data;

import android.support.annotation.NonNull;

import com.example.speechtotest.data.model.DictionaryWord;

import java.util.List;

public interface WordDataSource {

    interface LoadWordsCallback {

        void onWordsLoaded(List<DictionaryWord> wordList);

        void onDataNotAvailable();
    }

    interface GetWordCallback {

        void onWordLoaded(DictionaryWord dictionaryWord);

        void onDataNotAvailable();

    }

    void getWords(@NonNull LoadWordsCallback callback);

    void getWord(@NonNull GetWordCallback callback);

    void saveWord(@NonNull DictionaryWord dictionaryWord);

    void activateWord(@NonNull DictionaryWord dictionaryWord);

    void activateWord(@NonNull String word);

    void resetAllWords(@NonNull LoadWordsCallback callback);

    void refreshWords();

    void deleteAllWords();

    void deleteWord(@NonNull DictionaryWord dictionaryWord);

}
