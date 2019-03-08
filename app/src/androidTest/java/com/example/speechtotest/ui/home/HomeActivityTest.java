package com.example.speechtotest.ui.home;

import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.speechtotest.R;
import com.example.speechtotest.data.WordsRepository;
import com.example.speechtotest.data.source.model.DictionaryWord;
import com.example.speechtotest.fake_data_source.FakeDictionaryWords;
import com.example.speechtotest.ui.speech.SpeechActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;


/**
 * Created by mukesh on 27/02/19
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)     // this will help methods executed in an order
public class HomeActivityTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * this rule will launch the activity and also give us the reference of the activity
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    public Instrumentation.ActivityMonitor monitor =
            getInstrumentation().addMonitor(SpeechActivity.class.getName(), null, false);

    @Inject
    ViewModelProvider.NewInstanceFactory factory;

    private HomeViewModel homeViewModel;
    private HomeActivity homeActivity = null;
    private static final int WAIT_PERIOD = 5000;
    private static final int THREAD_SLEEP_TIME = 1000;
    private static final String DICTIONARY_WORD = "hello";

    @Before
    public void setUp() throws Exception {

        homeActivity = mActivityTestRule.getActivity();
        homeViewModel = ViewModelProviders.of(homeActivity, factory).get(HomeViewModel.class);

    }

    /**
     * make sure the home activity is launched
     */
    @Test
    public void test1_launch(){
        View view  = homeActivity.findViewById(R.id.btn_speak);
        assertNotNull(view);

        onView(allOf(withId(R.id.btn_speak), withText(R.string.speak))).check(matches(isDisplayed()));

    }

    /**
     * make sure the speech activity is launched after the onClick of 'speak' button
     */
    @Test
    public void test2_launchSpeechTest(){

        onView(allOf(withId(R.id.btn_speak), withText(R.string.speak))).check(matches(isDisplayed()));

        clickSpeakButton_opensSpeechActivity();

    }


    /**
     * get a mock speech text from speech activity
     * and test that, did HomeActivity receives it
     */
    @Test
    public void test3_getMockSpeechText_NegativeScenario(){

        clickSpeakButton_opensSpeechActivity("test1212");

        /**
         * this sleep time is needed for the toast message to be displayed
         * so that Espresso can read the toast message
         */
        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check if negative toast is displayed
        onView(withText(R.string.record_404)).inRoot(withDecorView(not(is(homeActivity.getWindow()
                .getDecorView())))).check(matches(isDisplayed()));

    }

    /**
     * get a mock speech text from speech activity
     * and test that, did HomeActivity receives it
     */
    @Test
    public void test4_getMockSpeechText_PositiveScenario(){

        clickSpeakButton_opensSpeechActivity(DICTIONARY_WORD);

        /**
         * this sleep time is needed for the toast message to be displayed
         * so that Espresso can read the toast message
         */
        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check if positive toast is displayed
        onView(withText(R.string.record_updated)).inRoot(withDecorView(not(is(homeActivity.getWindow()
                .getDecorView())))).check(matches(isDisplayed()));

        // Verify task is displayed on screen in the task list.
        onView(withItemText(DICTIONARY_WORD)).check(matches(isDisplayed()));

    }

    /**
     * this test focuses on checking the word counter updated
     * and background color changed to green
     * when a word in dictionary matches with the spoken word
     */
    @Test
    public void test5_checkWordCounterUpdated(){

        int helloCount = 20;

        assertNotNull(homeViewModel);

        DictionaryWord word = new DictionaryWord(DICTIONARY_WORD, helloCount, false);

        FakeDictionaryWords fakeDictionaryWords = new FakeDictionaryWords(word);

        /**
         * this sleep time is needed for the toast message to be displayed
         * so that Espresso can read the toast message
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homeViewModel.testSaveData(fakeDictionaryWords.getDictionaryWords());

        onView(withItemText(DICTIONARY_WORD)).check(matches(isDisplayed()));
        onView(withItemText(String.valueOf(helloCount))).check(matches(isDisplayed()));

        clickSpeakButton_opensSpeechActivity(DICTIONARY_WORD);

        // check if positive toast is displayed
        onView(withText(R.string.record_updated)).inRoot(withDecorView(not(is(homeActivity.getWindow()
                .getDecorView())))).check(matches(isDisplayed()));

        /**
         * as {@link DICTIONARY_WORD} word is sent by speech activity
         * so the frequenct value should have been incremented in the UI
         * to assert that case we are incrementing the value of {@link DICTIONARY_WORD} by 1
         */
        helloCount++;


        /**
         * this sleep time is needed for the toast message to be displayed
         * so that Espresso can read the toast message
         */
        try {
            Thread.sleep(WAIT_PERIOD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check if the dictionary word is displayed with the increased frequency
        onView(withItemText(DICTIONARY_WORD)).check(matches(isDisplayed()));
        onView(withItemText(String.valueOf(helloCount))).check(matches(isDisplayed()));

    }

    /**
     * A custom {@link Matcher} which matches an item in a {@link RecyclerView} by its text.
     * <p>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link RecyclerView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    private void clickSpeakButton_opensSpeechActivity(final String word){
        onView(withId(R.id.btn_speak)).perform(click());

        // check speechActivity launch
        Activity speechActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, WAIT_PERIOD);
        assertNotNull(speechActivity);

        // make sure the speech activity is launched by view assertion
        onView(allOf(withId(R.id.header), withText(R.string.speak_instruction))).check(matches(isDisplayed()));

        if (word != null) {
            speakAWord(speechActivity, word);
        }
    }

    private void clickSpeakButton_opensSpeechActivity(){
        onView(withId(R.id.btn_speak)).perform(click());

        // check speechActivity launch
        Activity speechActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, WAIT_PERIOD);
        assertNotNull(speechActivity);

        // make sure the speech activity is launched by view assertion
        onView(allOf(withId(R.id.header), withText(R.string.speak_instruction))).check(matches(isDisplayed()));
    }


    private void speakAWord(final Activity speechActivity, final String word){

        // pass result from speechActivity to homeActivity
        Intent intent = new Intent();
        intent.putExtra(SpeechActivity.SPEECH_DATA_KEY, word);
        speechActivity.setResult(Activity.RESULT_OK, intent);
        speechActivity.finish();
    }

    @After
    public void tearDown() throws Exception {

        homeActivity = null;

    }
}