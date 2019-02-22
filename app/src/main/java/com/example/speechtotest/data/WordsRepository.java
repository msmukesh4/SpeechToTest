package com.example.speechtotest.data;

import android.support.annotation.NonNull;

import com.example.speechtotest.data.source.local.WordsLocalDataSource;
import com.example.speechtotest.data.source.model.DictionaryWord;
import com.example.speechtotest.data.source.remote.WordsRemoteDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.speechtotest.util.Common.checkNotNull;


/**
 * Concrete implementation to load dictionary words from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 *
 * //TODO: Implement this class using LiveData.
 */
public class WordsRepository implements WordsDataSource {

//    private volatile static WordsRepository INSTANCE = null;

    private final WordsDataSource mWordsRemoteDataSource;

    private final WordsDataSource mWordsLocalDataSource;

    Map<String, DictionaryWord> mCachedwords;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;


    /**
     * making class singletone
     *
     * @param mWordsRemoteDataSource
     * @param mWordsLocalDataSource
     */
//    @Inject
    public WordsRepository(@NonNull WordsDataSource mWordsRemoteDataSource, @NonNull WordsDataSource mWordsLocalDataSource) {
        this.mWordsRemoteDataSource = checkNotNull(mWordsRemoteDataSource);
        this.mWordsLocalDataSource = checkNotNull(mWordsLocalDataSource);
    }

    /**
     * returns the single instance of the class creating it if necessary
     *
     * @param mWordsRemoteDataSource
     * @param mWordsLocalDataSource
     * @return the {@Link WordsRepository} instance
     */
//    public static WordsRepository getInstance(WordsDataSource mWordsRemoteDataSource, WordsDataSource mWordsLocalDataSource){
//
//        if (INSTANCE == null){
//            synchronized (WordsRepository.class){
//                if (INSTANCE == null){
//                    INSTANCE = new WordsRepository(mWordsRemoteDataSource, mWordsLocalDataSource);
//                }
//            }
//        }
//
//        return INSTANCE;
//    }


//    /**
//     * Used to force {@link #getInstance(WordsDataSource, WordsDataSource)} to create a new instance
//     * next time it's called.
//     */
//    public static void destroyInstance(){
//        INSTANCE = null;
//    }


    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadWordsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        checkNotNull(callback);

        // return data immediately with cache hit
        if (mCachedwords != null && !mCacheIsDirty){
            callback.onWordsLoaded(new ArrayList<DictionaryWord>(mCachedwords.values()));
        }

        if (mCacheIsDirty){
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mWordsLocalDataSource.getWords(new LoadWordsCallback() {
                @Override
                public void onWordsLoaded(List<DictionaryWord> wordList) {
                    refreshCache(wordList);
                    callback.onWordsLoaded(wordList);
                }

                @Override
                public void onDataNotAvailable() {
                    // data not available in local so querying the remote data store
                    getTasksFromRemoteDataSource(callback);
                }
            });
        }
    }

    /**
     * Gets words from local data source (SQLite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     * <p>
     * Note: {@link GetWordCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getWord(@NonNull final String word, @NonNull final GetWordCallback callback) {
        checkNotNull(word);
        checkNotNull(callback);

        DictionaryWord dictionaryWord = getDictionaryWordFromCache(word);

        // Respond immediately with cache if available
        if (dictionaryWord != null && !mCacheIsDirty){
            callback.onWordLoaded(dictionaryWord);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mWordsLocalDataSource.getWord(word, new GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {

                // Do in memory cache update to keep the app UI up to date
                if (mCachedwords == null){
                    mCachedwords = new LinkedHashMap<>();
                }
                mCachedwords.put(dictionaryWord.getWord().toLowerCase(), dictionaryWord);

                callback.onWordLoaded(dictionaryWord);
            }

            @Override
            public void onDataNotAvailable() {
                mWordsRemoteDataSource.getWord(word, new GetWordCallback() {
                    @Override
                    public void onWordLoaded(DictionaryWord dictionaryWord) {
                        if (dictionaryWord == null){
                            onDataNotAvailable();
                            return;
                        }

                        // Do in memory cache
                        if (mCachedwords == null){
                            mCachedwords = new LinkedHashMap<>();
                        }
                        mCachedwords.put(dictionaryWord.getWord().toLowerCase(), dictionaryWord);

                        callback.onWordLoaded(dictionaryWord);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }


    @Override
    public void saveWord(@NonNull DictionaryWord dictionaryWord) {
        checkNotNull(dictionaryWord);

        mWordsRemoteDataSource.saveWord(dictionaryWord);
        mWordsLocalDataSource.saveWord(dictionaryWord);

        if (mCachedwords == null){
            mCachedwords = new LinkedHashMap<>();
        }
        mCachedwords.put(dictionaryWord.getWord().toLowerCase(), dictionaryWord);
    }

    @Override
    public void activateWord(@NonNull DictionaryWord dictionaryWord) {
        checkNotNull(dictionaryWord);

        DictionaryWord activeWord = new DictionaryWord(dictionaryWord.getWord(), dictionaryWord.getFrequency(), true);
        activeWord.incrementFrequency();

        mWordsRemoteDataSource.activateWord(activeWord);
        mWordsLocalDataSource.activateWord(activeWord);

        if (mCachedwords == null){
            mCachedwords = new LinkedHashMap<>();
        }
        mCachedwords.put(dictionaryWord.getWord().toLowerCase(), activeWord);
    }

    @Override
    public void activateWord(@NonNull String word) {
        checkNotNull(word);
        activateWord(getDictionaryWordFromCache(word));
    }

    @Override
    public void resetAllWords() {

        mWordsRemoteDataSource.resetAllWords();
        mWordsLocalDataSource.resetAllWords();

        // resetting the words in local cache
        if (mCachedwords == null){
            mCachedwords = new LinkedHashMap<>();
        }
        for (DictionaryWord word : mCachedwords.values()) {
            if (word.isActive()) {
                word.setActive(false);
                mCachedwords.put(word.getWord().toLowerCase(), word);
            }
        }
    }

    @Override
    public void refreshWords() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllWords() {

        mWordsRemoteDataSource.deleteAllWords();
        mWordsLocalDataSource.deleteAllWords();

        if (mCachedwords == null){
            mCachedwords = new LinkedHashMap<>();
        }
        mCachedwords.clear();
    }

    @Override
    public void deleteWord(@NonNull DictionaryWord dictionaryWord) {

    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadWordsCallback callback) {
        mWordsRemoteDataSource.getWords(new LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<DictionaryWord> dictionaryWords) {
                refreshCache(dictionaryWords);
                refreshLocalDataSource(dictionaryWords);

                callback.onWordsLoaded(new ArrayList<>(mCachedwords.values()));
            }

            @Override
            public void onDataNotAvailable() {

                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<DictionaryWord> dictionaryWords) {
        if (mCachedwords == null){
            mCachedwords = new LinkedHashMap<>();
        }
        mCachedwords.clear();
        for (DictionaryWord word: dictionaryWords) {
            mCachedwords.put(word.getWord().toLowerCase(), word);
        }

        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<DictionaryWord> dictionaryWords) {
        mWordsLocalDataSource.deleteAllWords();
        for (DictionaryWord word : dictionaryWords) {
            mWordsLocalDataSource.saveWord(word);
        }
    }

    private DictionaryWord getDictionaryWordFromCache(@NonNull String word) {
        checkNotNull(word);

        if (mCachedwords != null && !mCachedwords.isEmpty()){
            return mCachedwords.get(word.toLowerCase());
        } else {
            return null;
        }
    }

}
