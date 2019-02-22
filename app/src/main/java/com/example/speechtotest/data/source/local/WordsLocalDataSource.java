package com.example.speechtotest.data.source.local;

import android.support.annotation.NonNull;

import com.example.speechtotest.data.WordsDataSource;
import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.source.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.source.model.DictionaryWord;
import com.example.speechtotest.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

import static com.example.speechtotest.util.Common.checkNotNull;

public class WordsLocalDataSource implements WordsDataSource {

//    private volatile static WordsLocalDataSource INSTANCE;

    private DictionaryWordDAO dictionaryWordDAO;

    private AppExecutors appExecutors;

    @Inject
    public WordsLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull SpeechToTextDatabase speechToTextDatabase){
        this.appExecutors = appExecutors;
        this.dictionaryWordDAO = speechToTextDatabase.dictionaryWordDAO();
    }

//    public static WordsLocalDataSource newInstance(@NonNull AppExecutors appExecutors,
//                                                   @NonNull DictionaryWordDAO dictionaryWordDAO){
//        if (INSTANCE == null){
//            synchronized (WordsLocalDataSource.class){
//                if (INSTANCE == null){
//                    INSTANCE = new WordsLocalDataSource(appExecutors, dictionaryWordDAO);
//                }
//            }
//        }
//        return INSTANCE;
//    }

    /**
     * Note: {@link LoadWordsCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        Runnable runnable = () -> {
            final List<DictionaryWord> dictionaryWords = dictionaryWordDAO.getAllDictionaryWords();
            appExecutors.mainThread().execute(() -> {
                if (dictionaryWords.isEmpty()){
                    callback.onDataNotAvailable();
                } else {
                    callback.onWordsLoaded(dictionaryWords);
                }
            });
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getWord(@NonNull final String word, @NonNull final GetWordCallback callback) {
        Runnable runnable = () -> {
            final DictionaryWord dictionaryWord = dictionaryWordDAO.getDictionaryWord(word);
            appExecutors.mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    if (dictionaryWord == null){
                        callback.onDataNotAvailable();
                    } else {
                       callback.onWordLoaded(dictionaryWord);
                    }
                }
            });
        };
        appExecutors.diskIO().execute(runnable);
    }


    @Override
    public void saveWord(@NonNull final DictionaryWord dictionaryWord) {
        checkNotNull(dictionaryWord);
        Runnable runnable = () -> dictionaryWordDAO.insert(dictionaryWord);
        appExecutors.diskIO().execute(runnable);
    }



    @Override
    public void activateWord(@NonNull final String word) {
        checkNotNull(word);
        final Runnable runnable = () -> dictionaryWordDAO.active(true, word);
        appExecutors.diskIO().execute(runnable);
    }


    @Override
    public void activateWord(@NonNull final DictionaryWord dictionaryWord) {
        checkNotNull(dictionaryWord);
        final Runnable runnable = () -> dictionaryWordDAO.update(dictionaryWord);
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void resetAllWords() {
        final Runnable runnable = () -> dictionaryWordDAO.resetActiveWords(false, true);
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshWords() {
        // Not required because the {@link WordsRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllWords() {
        final Runnable runnable = () -> dictionaryWordDAO.deleteAll();
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteWord(@NonNull final DictionaryWord dictionaryWord) {
        checkNotNull(dictionaryWord);
        final Runnable runnable = () -> dictionaryWordDAO.delete(dictionaryWord.getWord());
        appExecutors.diskIO().execute(runnable);
    }
}
