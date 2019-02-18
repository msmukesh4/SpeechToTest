package com.example.speechtotest.data.local.db.operations;

import android.os.AsyncTask;

import com.example.speechtotest.data.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.model.DictionaryWord;

import java.util.List;

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
