package com.example.speechtotest.data;

import android.support.annotation.NonNull;

import com.example.speechtotest.data.model.DictionaryWord;

import java.util.List;

public interface WordsDataSource {

    interface LoadWordsCallback {

        void onWordsLoaded(List<DictionaryWord> wordList);

        void onDataNotAvailable();
    }

    interface GetWordCallback {

        void onWordLoaded(DictionaryWord dictionaryWord);

        void onDataNotAvailable();

    }

    void getWords(@NonNull LoadWordsCallback callback);

    void getWord(@NonNull String word, @NonNull GetWordCallback callback);

    void saveWord(@NonNull DictionaryWord dictionaryWord);

    void activateWord(@NonNull DictionaryWord dictionaryWord);

    void activateWord(@NonNull String word);

    void resetAllWords();

    void refreshWords();

    void deleteAllWords();

    void deleteWord(@NonNull DictionaryWord dictionaryWord);

}
