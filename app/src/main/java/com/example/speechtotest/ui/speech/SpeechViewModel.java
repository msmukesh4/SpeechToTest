package com.example.speechtotest.ui.speech;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.ui.base.BaseViewModel;
import com.example.speechtotest.util.StringUtils;

/**
 * Created by mukesh on 05/02/19
 *
 *  TODO implement remove the {@link BaseActivity} reference from this class as it can leak. Try implementing the error mechanism through {@link MutableLiveData}
 *  TODO after implementing the above todo remove StaticFieldLeak ignore statement from lint.xml file
 */
public class SpeechViewModel extends BaseViewModel {


    private WordsRepository wordsRepository;

    public SpeechViewModel(Application application, WordsRepository wordsRepository) {
        super(application);

        this.wordsRepository = wordsRepository;
    }

    @Override
    protected void setUp() { }

    /**
     * App specific string replacement
     * @param s : string to be replaced
     * @return manipulated string
     */
    protected String replaceStreetText(String s) {
        return StringUtils.replaceAppText(s);
    }
}
