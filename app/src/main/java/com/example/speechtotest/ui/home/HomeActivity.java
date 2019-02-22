package com.example.speechtotest.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.speechtotest.R;
import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.model.DictionaryWord;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.ui.speech.SpeechActivity;
import com.example.speechtotest.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mukesh on 30/01/19
 * This is the homepage of the application
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    public static final int SPEECH_REQUEST_CODE = 200;
    private RecyclerView dictionaryRecyclerView;
    private Button speak;
    private List<DictionaryWord> keyWords;
    private DictionaryAdapter dictionaryAdapter;
    private HomeViewModel homeViewModel;

    @Inject
    ViewModelProvider.NewInstanceFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dictionaryRecyclerView = findViewById(R.id.list_view_dictionary);
        speak = findViewById(R.id.btn_speak);

        // register onClickListener
        speak.setOnClickListener(this);

        ((SpeechToTextApplication) getApplication())
                .getApplicationComponent().inject(this);

        // setUp the activity
        setUp();
    }

    @Override
    protected void setUp() {
        // Use a Factory to inject dependencies into the ViewModel
//        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());

        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);

        homeViewModel.setUp(this);

        dictionaryAdapter = new DictionaryAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        dictionaryRecyclerView.setLayoutManager(layoutManager);
        dictionaryRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        dictionaryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dictionaryRecyclerView.setAdapter(dictionaryAdapter);
        registerObservers();
    }

    /**
     * register all the LiveData observers
     * and notify the UI when there is a data change
     */
    private void registerObservers() {

        // observe the change in dictionary words
        this.homeViewModel.getAllWords().observe(this, new Observer<List<DictionaryWord>>() {
            @Override
            public void onChanged(@Nullable List<DictionaryWord> words) {
                Log.e(TAG, "onChanged: data changed");

                // notifying adapter about change in dictionary data
                dictionaryAdapter.setWords(words);
            }
        });
    }


    /**
     * registering all the onClick listeners
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_speak:
                startActivityForResult(new Intent(this,
                        SpeechActivity.class), SPEECH_REQUEST_CODE);
        }
    }

    /**
     * this method receives the result send by speech activity
     * @param requestCode : request code provided while starting activity
     * @param resultCode : RESULT_OK / RESULT_CANCELED
     * @param data : contains user spoken word
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SPEECH_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null){
                    this.homeViewModel.checkSpeechText(data.getStringExtra("speech"));
                }
                break;
        }
    }

}