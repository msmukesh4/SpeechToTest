package com.example.speechtotest.data.source.local.db.operations;

import android.os.AsyncTask;

import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.source.model.DictionaryWord;

/**
 * Created by mukesh on 04/02/19
 */
public class UpdateWordAsyncTask extends AsyncTask<DictionaryWord, Void, Void> {

    private DictionaryWordDAO dictionaryWordDAO;

    public UpdateWordAsyncTask(DictionaryWordDAO dictionaryWordDAO) {
        this.dictionaryWordDAO = dictionaryWordDAO;
    }

    @Override
    protected Void doInBackground(DictionaryWord... dictionaryWord) {
        this.dictionaryWordDAO.update(dictionaryWord[0]);
        return null;
    }
}
