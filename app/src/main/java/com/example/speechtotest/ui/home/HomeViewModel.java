package com.example.speechtotest.ui.home;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.speechtotest.R;
import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.data.WordsDataSource;
import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.remote.APIClient;
import com.example.speechtotest.data.remote.response.DictionaryAPIResponse;
import com.example.speechtotest.data.local.repositories.DictionaryWordRepository;
import com.example.speechtotest.data.model.DictionaryWord;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mukesh on 04/02/19
 * ViewModel Class of homeActivity
 */
public class HomeViewModel extends BaseViewModel {

    // live data variable for registering change in data
    private MutableLiveData<List<DictionaryWord>> keyWords = new MutableLiveData<>();

    // @UnUsed
    // this repository has the access all the data of dictionary words
    private DictionaryWordRepository dictionaryWordRepository;

    private static final String TAG = HomeViewModel.class.getSimpleName();
    private BaseActivity activity;

    private WordsRepository wordsRepository;


    /**
     *  SpeechToTextApplication.getApplicationComponent().inject(this);
     *  not needed anymore as wordsRepository is injected from ViewModelFactory
     */
    public HomeViewModel(@NonNull Application application, WordsRepository wordsRepository) {
        super(application);

        this.wordsRepository = wordsRepository;

    }

    @Override
    protected void setUp(BaseActivity activity) {
        Log.e(TAG, "setUp::wordsRepository: "+wordsRepository );
        this.activity = activity;
        resetActiveWords();
        fetchAndSaveData();
    }

    /**
     * method to fetch dictionary data from the server
     * and take action to save the data in local DB
     */
    private void fetchAndSaveData(){
        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<DictionaryWord> wordList) {
                if (wordList != null)
                    keyWords.setValue(wordList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(TAG, "Error while fetching dictionary data: ");
                activity.showToast(R.string.something_went_wrong);
            }
        });
    }
//    private void fetchAndSaveData() {
//        APIClient.getAPIService(APIClient.LOG_REQ_RES_BODY_HEADERS)
//                .getDictionaryData().enqueue(new Callback<DictionaryAPIResponse>() {
//            @Override
//            public void onResponse(Call<DictionaryAPIResponse> call, Response<DictionaryAPIResponse> response) {
//                if (response.isSuccessful()) {
//                    List<DictionaryWord> words = response.body().getDictionaryWord();
//                    Log.e(TAG, "onResponse: " + words.toString());
//
//                    if (words != null && words.size() > 0) {
//                        saveData(words);
//                        resetActiveWords();
//                    }
//
//                } else {
//                    Log.e(TAG, "Error while fetching dictionary data: " + response.code());
////                    Toast.makeText(getApplicationContext(), R.string.something_went_wrong, Toast.LENGTH_LONG).show();
//                    activity.showToast(R.string.something_went_wrong);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DictionaryAPIResponse> call, Throwable t) {
//                Log.e(TAG, "Error while fetching dictionary data: " + t.getMessage());
////                Toast.makeText(getApplicationContext(), R.string.something_went_wrong, Toast.LENGTH_LONG).show();
//                activity.showToast(R.string.something_went_wrong);
//            }
//        });
//    }

    /**
     * find the DictionaryWord with the :keyWord and
     * update frequency and background of the spoken word
     *
     * if word not found display an error message as "Record not found"
     *
     * @param keyWord : search key word
     */
    protected void checkSpeechText(String keyWord) {
        Log.d(TAG, "checkSpeechText: " + keyWord);

        this.resetActiveWords();

        wordsRepository.getWord(keyWord, new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {
                wordsRepository.activateWord(keyWord);

                wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
                    @Override
                    public void onWordsLoaded(List<DictionaryWord> wordList) {
                        if (wordList != null)
                            keyWords.setValue(wordList);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Log.e(TAG, "Error while fetching dictionary data: ");
                        activity.showToast(R.string.something_went_wrong);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                activity.showToast(R.string.record_404);
            }
        });
    }

    /**
     * @Unused
     * this method is used to call
     * repository method to save data
     * @param words : words to be saved
     */
    private void saveData(List<DictionaryWord> words) {
        Log.d(TAG, "saveData: inserting words");
        dictionaryWordRepository.insertAll(words);
    }

    /**
     * retrieve the list of all words saved in the local DB
     * @return : List of words
     */
    protected LiveData<List<DictionaryWord>> getAllWords() {
        return keyWords;
    }

    /**
     * @Unused
     * update a dictionary word
     * @param word
     */
    private void update(DictionaryWord word){
        dictionaryWordRepository.updateDictionaryWord(word);
    }

    /**
     * @UnUsed
     * @param isActive
     * @param frequency
     * @param word
     */
    private void update(boolean isActive, int frequency, String word){
        dictionaryWordRepository.updateDictionaryWord(isActive, frequency, word);
    }

    /**
     * this method is used to reset all the Local word's isActive to false
     * this will change the background of the active words
     * from GREY --> GREEN
     */
    private void resetActiveWords(){
        wordsRepository.resetAllWords();
    }


    /**
     * test helper function to save data
     * @param words
     */
    public void testSaveData(List<DictionaryWord> words){
        this.saveData(words);
    }

    /**
     * test helper function to get key words
     * @return
     */
    public List<DictionaryWord> testGetKeyWords() {
        return keyWords.getValue();
    }
}
