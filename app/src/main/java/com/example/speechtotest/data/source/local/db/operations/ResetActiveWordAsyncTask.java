package com.example.speechtotest.data.source.local.db.operations;

import android.os.AsyncTask;

import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;

/**
 * Created by mukesh on 04/02/19
 */
public class ResetActiveWordAsyncTask extends AsyncTask<Boolean, Void, Void> {

    private DictionaryWordDAO dictionaryWordDAO;

    public ResetActiveWordAsyncTask(DictionaryWordDAO dictionaryWordDAO) {
        this.dictionaryWordDAO = dictionaryWordDAO;
    }

    @Override
    protected Void doInBackground(Boolean... isActive) {
        this.dictionaryWordDAO.resetActiveWords(isActive[0], true);
        return null;
    }
}
