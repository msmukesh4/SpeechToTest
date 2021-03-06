package com.example.speechtotest.ui.speech;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechtotest.R;
import com.example.speechtotest.SpeechToTextApplication;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by mukesh on 30/01/19
 */
public class SpeechActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SpeechActivity.class.getSimpleName();
    private TextView text;
    private Button btnContinue;
    public static final int SPEECH_REQUEST_CODE = 200;
    public static final String SPEECH_DATA_KEY = "speech";
    private SpeechViewModel speechViewModel;
    private RelativeLayout rlSpokeLayout;

    @Inject
    ViewModelProvider.NewInstanceFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);


        text = findViewById(R.id.text);
        rlSpokeLayout = findViewById(R.id.rl_spoke_layout);
        rlSpokeLayout.setVisibility(View.INVISIBLE);

        btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);

        ((SpeechToTextApplication) getApplication())
                .getApplicationComponent().inject(this);

        // setUp the activity
        setUp();

    }


    @Override
    protected void setUp() {
//        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        speechViewModel = ViewModelProviders.of(this, factory).get(SpeechViewModel.class);
    }


    /**
     * @param view : mic-icon
     * helper method to get user's speech
     */
    public void getUserSpeech(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } else {
            Toast.makeText(this, R.string.speech_to_text_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * callback method after user finished speaking
     * and accepts the text
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.e(TAG, "onActivityResult: "+result.get(0));
                    refreshText(result.get(0));
                }
                break;
        }
    }


    public void refreshText(String text){
        this.text.setText(speechViewModel.replaceStreetText(text));
        rlSpokeLayout.setVisibility(View.VISIBLE);
    }


    /**
     * @param view : clicked view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continue:
                // returning data to parent activity
                Intent intent = new Intent();
                if (text.getText().length() > 0) {
                    intent.putExtra(SPEECH_DATA_KEY, text.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                } else
                    setResult(Activity.RESULT_CANCELED);

                finish();
        }
    }
}
