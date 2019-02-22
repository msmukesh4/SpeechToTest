package com.example.speechtotest.data.source.local.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.source.local.db.operations.InsertWordAsyncTask;
import com.example.speechtotest.data.source.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.source.local.db.operations.ResetActiveWordAsyncTask;
import com.example.speechtotest.data.source.local.db.operations.UpdateWordAsyncTask;
import com.example.speechtotest.data.source.model.DictionaryWord;

import java.util.List;

/**
 * Created by mukesh on 04/02/19
 */
public class DictionaryWordRepository {

    private DictionaryWordDAO dictionaryWordDAO;
    private LiveData<List<DictionaryWord>> dictionaryWords;

    public DictionaryWordRepository(Application application) {
        SpeechToTextDatabase db = SpeechToTextDatabase.getInstance(application);
        this.dictionaryWordDAO = db.dictionaryWordDAO();
//        this.dictionaryWords = this.dictionaryWordDAO.getAllDictionaryWords();
    }

    public LiveData<List<DictionaryWord>> getDictionaryWords() {
        return dictionaryWords;
    }

    public void insertAll(List<DictionaryWord> dictionaryWords){
        // Room doesn't allow long running operations on main thread
        // Insert into DB should be done in separate thread
        new InsertWordAsyncTask(dictionaryWordDAO).execute(dictionaryWords);
    }

    // update the whole word
    public void updateDictionaryWord(DictionaryWord dictionaryWord){
        new UpdateWordAsyncTask(this.dictionaryWordDAO).execute(dictionaryWord);
    }

    // update frequency and isActive
    public void updateDictionaryWord(boolean isActive, int frequency,String word){
        this.dictionaryWordDAO.update(isActive, frequency, word);
    }

    public void resetActiveWords(boolean isActive){
        new ResetActiveWordAsyncTask(dictionaryWordDAO).execute(isActive);
    }
}
