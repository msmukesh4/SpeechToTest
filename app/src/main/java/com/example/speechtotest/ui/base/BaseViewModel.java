package com.example.speechtotest.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.local.WordsLocalDataSource;
import com.example.speechtotest.util.AppExecutors;

/**
 * Created by mukesh on 04/02/19
 *
 *
 *
 */
public abstract class BaseViewModel extends AndroidViewModel {

    protected WordsRepository wordsRepository;

    protected BaseViewModel(@NonNull Application application){
        super(application);
//        WordsLocalDataSource localDataSource = WordsLocalDataSource.newInstance()
//        wordsRepository = WordsRepository.getInstance()
    }

    /**
     * used for viewModel setup
     * here you can bind activity into viewModel
     */
    protected abstract void setUp(BaseActivity activity);
}
