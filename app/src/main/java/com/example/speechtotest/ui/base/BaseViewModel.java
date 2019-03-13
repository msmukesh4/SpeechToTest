package com.example.speechtotest.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mukesh on 04/02/19
 *
 *
 *
 */
public abstract class BaseViewModel extends AndroidViewModel {

    private AtomicBoolean isRunningTest;

    protected BaseViewModel(@NonNull Application application){
        super(application);
    }

    /**
     * helper function to check if espresso test is running
     * @return
     */
    public synchronized boolean isRunningTest () {
        if (null == isRunningTest) {
            boolean istest;

            try {
                Class.forName ("android.support.test.espresso.Espresso");
                istest = true;
            } catch (ClassNotFoundException e) {
                istest = false;
            }

            isRunningTest = new AtomicBoolean(istest);
        }
        return isRunningTest.get();
    }

    /**
     * used for viewModel setup
     */
    protected abstract void setUp();
}
