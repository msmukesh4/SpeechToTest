package com.example.speechtotest.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by mukesh on 04/02/19
 *
 *
 *
 */
public abstract class BaseViewModel extends AndroidViewModel {

    protected BaseViewModel(@NonNull Application application){
        super(application);
    }

    /**
     * used for viewModel setup
     * here you can bind activity into viewModel
     */
    protected abstract void setUp(BaseActivity activity);
}
