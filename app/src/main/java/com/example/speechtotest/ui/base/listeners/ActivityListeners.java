package com.example.speechtotest.ui.base.listeners;

import android.support.annotation.StringRes;

/**
 * Created by mukesh on 05/02/19
 */
public interface ActivityListeners {

    /**
     * helper function for displaying toast
     * @param msg : displayMsg
     */
    void showToast(String msg);

    void showToast(@StringRes int msg);


    /**
     * helper function for showing dialog
     * @param header : dialog title
     * @param desc : dialog Message
     */
    void showDialog(String header, String desc);

    void showDialog(@StringRes int header, @StringRes int desc);


    /**
     * helper function for showing Snackbar
     * @param msg : snackbar Test
     */
    void showError(String msg);

    void showError(@StringRes int msg);

}
