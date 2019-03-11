package com.example.speechtotest.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.speechtotest.R;
import com.example.speechtotest.ui.base.BaseActivity;
import com.example.speechtotest.ui.home.HomeActivity;

/**
 * Created by mukesh on 30/01/19
 */
public class SplashActivity extends BaseActivity {

    protected static final int TIMEOUT = 2000;
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUp();

    }

    @Override
    protected void setUp() {
        registerHandler();
    }

   private void registerHandler(){
       Handler handler = new Handler();
       handler.postDelayed(() -> {
           Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
           startActivity(intent);
           finish();
       }, TIMEOUT);
   }
}
