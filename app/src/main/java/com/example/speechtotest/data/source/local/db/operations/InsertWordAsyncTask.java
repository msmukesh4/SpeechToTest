package com.example.speechtotest.data.source.local.db.operations;

import android.os.AsyncTask;

import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.source.model.DictionaryWord;

import java.util.List;

/**
 * Created by mukesh on 04/02/19
 */
public class InsertWordAsyncTask extends AsyncTask<List<DictionaryWord>, Void, Void> {

    private DictionaryWordDAO dictionaryWordDAO;

    public InsertWordAsyncTask(DictionaryWordDAO dictionaryWordDAO) {
        this.dictionaryWordDAO = dictionaryWordDAO;
    }

    @Override
    protected Void doInBackground(List<DictionaryWord>... dictionaryWords) {

        if (dictionaryWords.length > 0){
            for (DictionaryWord word: dictionaryWords[0]) {
                dictionaryWordDAO.insert(word);
            }
        }

        return null;
    }
}
