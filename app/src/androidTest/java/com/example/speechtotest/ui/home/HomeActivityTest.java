package com.example.speechtotest.ui.home;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.speechtotest.R;
import com.example.speechtotest.ui.speech.SpeechActivity;
import com.example.speechtotest.ui.splash.SplashActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by mukesh on 27/02/19
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)     // this will help methods executed in an order
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    public Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(SpeechActivity.class.getName(), null, false);

    private HomeActivity homeActivity = null;
    private static final int WAIT_PERIOD = 5000;

    @Before
    public void setUp() throws Exception {

        homeActivity = mActivityTestRule.getActivity();

    }

    /**
     * make sure the home activity is launched
     */
    @Test
    public void test1Launch(){
        View view  = homeActivity.findViewById(R.id.btn_speak);
        assertNotNull(view);

        onView(allOf(withId(R.id.btn_speak), withText(R.string.speak))).check(matches(isDisplayed()));

    }

    /**
     * make sure the speech activity is launched after the onClick of 'speak' button
     */
    @Test
    public void test2LaunchSpeechTest(){

        onView(allOf(withId(R.id.btn_speak), withText(R.string.speak))).check(matches(isDisplayed()));

        onView(withId(R.id.btn_speak)).perform(click());

        Activity speechActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, WAIT_PERIOD);
        assertNotNull(speechActivity);

        // make sure the speech activity is launched by view assertion
        onView(allOf(withId(R.id.header), withText(R.string.speak_instruction))).check(matches(isDisplayed()));

    }


    /**
     * get a mock speech text from speech activity
     * and test test did HomeActivity receives it
     */
    @Test
    public void test3GetMockSpeechText(){

        onView(withId(R.id.btn_speak)).perform(click());

        // check speechActivity launch
        Activity speechActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, WAIT_PERIOD);
        assertNotNull(speechActivity);

        // make sure the speech activity is launched by view assertion
        onView(allOf(withId(R.id.header), withText(R.string.speak_instruction))).check(matches(isDisplayed()));

        // pass result from speechActivity to homeActivity
        Intent intent = new Intent();
        intent.putExtra(SpeechActivity.SPEECH_DATA_KEY, "test");
        speechActivity.setResult(Activity.RESULT_OK, intent);
        speechActivity.finish();

        // check if the correct result is received in homeActivity
        // ...

    }

    @After
    public void tearDown() throws Exception {

        homeActivity = null;

    }
}