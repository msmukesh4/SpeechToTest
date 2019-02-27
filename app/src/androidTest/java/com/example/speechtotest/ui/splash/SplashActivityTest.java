package com.example.speechtotest.ui.splash;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.speechtotest.R;
import com.example.speechtotest.ui.home.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Created by mukesh on 27/02/19
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)     // this will help methods executed in an order
public class SplashActivityTest {

    // this rule will launch the activity and also give us the reference of the activity
    @Rule
    public ActivityTestRule<SplashActivity> mSplashActivity = new ActivityTestRule<>(SplashActivity.class);

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(HomeActivity.class.getName(), null, false);

    private SplashActivity splashActivity = null;
    private static final int WAIT_PERIOD = SplashActivity.TIMEOUT + 5000;

    @Before
    public void setUp() throws Exception {

        this.splashActivity = mSplashActivity.getActivity();

    }

    @Test
    public void test1Launch(){

        // make sure splash activity is launched
        View view = this.splashActivity.findViewById(R.id.splash_test);
        assertNotNull(view);

    }

    // check if the second activity starts onClick of splash text view
    @Test
    public void test2HomeActivityLaunch(){

        // make sure splash activity is launched
        View view = this.splashActivity.findViewById(R.id.splash_test);
        assertNotNull(view);


        /**
         * get the reference of second activity and
         * check if the activity is launched within the given Timeout period
         */
        Activity homeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, WAIT_PERIOD);
        assertNotNull(homeActivity);

    }

    @After
    public void tearDown() throws Exception {
        splashActivity = null;
    }
}