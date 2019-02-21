package com.example.speechtotest.ui.speech;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.ui.base.BaseViewModel;
import com.example.speechtotest.util.StringUtils;

/**
 * Created by mukesh on 05/02/19
 */
public class SpeechViewModel extends BaseViewModel {

    private BaseActivity activity;

    private WordsRepository wordsRepository;

    public SpeechViewModel(Application application, WordsRepository wordsRepository) {
        super(application);

        this.wordsRepository = wordsRepository;
    }

    @Override
    protected void setUp(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * App specific string replacement
     * @param s : string to be replaced
     * @return manipulated string
     */
    protected String replaceStreetText(String s) {
        return StringUtils.replaceAppText(s);
    }
}
