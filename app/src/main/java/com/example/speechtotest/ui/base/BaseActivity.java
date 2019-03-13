package com.example.speechtotest.ui.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.speechtotest.ui.base.listeners.ActivityListeners;

import java.util.concurrent.atomic.AtomicBoolean;

import dagger.android.AndroidInjection;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

/**
 * Created by mukesh on 30/01/19
 *
 * This is a base activity containing activity helper functions
 * all the common helper functions should be added here
 *
 * This methods can also be called from bind ViewModel
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityListeners {

    private AtomicBoolean isRunningTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * helper function for displaying toast
     * @param msg : displayMsg
     */
    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(@StringRes int msg) {
        showToast(getString(msg));
    }

    /**
     * helper function for showing dialog
     * @param header : dialog title
     * @param desc : dialog Message
     */
    @Override
    public void showDialog(String header, String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(header);
        builder.setMessage(desc);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showDialog(@StringRes int header, @StringRes int desc) {
        this.showDialog(getString(header), getString(desc));
    }

    /**
     * helper function for showing Snackbar
     * @param msg : {@link Snackbar} Test
     */
    @Override
    public void showError(String msg) {
        Snackbar.make(getWindow().getDecorView().getRootView(), msg,
                Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showError(@StringRes int msg) {
        showError(getString(msg));
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

            isRunningTest = new AtomicBoolean (istest);
        }
        return isRunningTest.get();
    }

    /**
     * used for activity setup
     * here you can bind view holder
     */
    protected abstract void setUp();
}
