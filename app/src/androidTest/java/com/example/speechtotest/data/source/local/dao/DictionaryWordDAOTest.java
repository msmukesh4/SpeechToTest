package com.example.speechtotest.data.source.local.dao;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.speechtotest.data.source.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.source.model.DictionaryWord;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.ui.home.HomeViewModel;
import com.example.speechtotest.ui.splash.SplashActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by mukesh on 08/03/19
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DictionaryWordDAOTest {

    // this rule will launch the activity and also give us the reference of the activity
    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivity = new ActivityTestRule<>(HomeActivity.class);


    private static final DictionaryWord mWord = new DictionaryWord("hello", 20, false);

    private SpeechToTextDatabase mDatabase;

    @Before
    public void setUp() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(mHomeActivity.getActivity().getApplicationContext(),
                SpeechToTextDatabase.class).build();
    }

    @Before
    public void insertData() throws Exception {
        // inserting a dictionary Word
        mDatabase.dictionaryWordDAO().insert(mWord);
    }

    @Test
    public void test1_insertWordAndGetByName(){

        // getting the dictionary Word from database using word
        DictionaryWord loaded = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());

        // compare the words
        assertWord(loaded, mWord);
    }

    @Test
    public void test2_insertTaskIgnoreOnConflict(){

        // when a Dictionary word with the same word is inserted
        // word is a PrimaryKey
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord(mWord.getWord(), 21, true));

        // getting the dictionary Word from database using word
        DictionaryWord loaded = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());

        // compare the words
        assertWord(loaded, mWord);
    }

    @Test
    public void test3_insertTaskAndGetTasks(){

        // getting the dictionary Words from database using word
        List<DictionaryWord> loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // check there is only one Dictionary word
        assertThat(loadedTasks.size(), is(1));

        // compare the words
        assertWord(loadedTasks.get(0), mWord);
    }

    @Test
    public void test4_insertTasksAndGetTasks_InDescOrder(){

        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task2", 25, false));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task3", 8, false));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task4", 81, false));

        // getting the dictionary Words from database using word
        List<DictionaryWord> loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // check there are 4 words loaded
        assertThat(loadedTasks.size(), is(4));

        // check if the tasks are in descending order
        assertWordsOrder(loadedTasks);
    }

    @Test
    public void test5_UpdateTaskAndCheck(){

        // update a word
        mDatabase.dictionaryWordDAO().update(true, mWord.getFrequency() + 1,  mWord.getWord());

        // check if thw word is activated
        DictionaryWord loaded = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());

        // check if the word is activated or not
        assertWord(loaded, mWord.getWord(), mWord.getFrequency() + 1, true);


        // toggle active check
        mDatabase.dictionaryWordDAO().update(false, mWord.getFrequency() + 1,  mWord.getWord());
        loaded = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());
        assertWord(loaded, mWord.getWord(), mWord.getFrequency() + 1, false);
    }

    @Test
    public void test6_activateTaskAndCheck(){

        // activate a word
        mDatabase.dictionaryWordDAO().active(true, mWord.getWord());

        // check if thw word is activated
        DictionaryWord loaded = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());

        // check if the word is activated or not
        assertWord(loaded, mWord.getWord(), mWord.getFrequency(), true);
    }

    @Test
    public void test7_resetAllActiveWords(){

        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task2", 25, true));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task3", 8, true));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task4", 81, false));

        // reset all the words
        mDatabase.dictionaryWordDAO().resetActiveWords(false, true);

        // getting the dictionary Words from database
        List<DictionaryWord> loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // check there are 4 words loaded
        assertThat(loadedTasks.size(), is(4));

        // check if the tasks are in descending order
        assertWordsDeactivated(loadedTasks);
    }

    @Test
    public void test8_deleteDictionaryWordByWord(){

        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task2", 25, true));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task3", 8, true));

        // delete a word
        mDatabase.dictionaryWordDAO().delete(mWord.getWord());

        // query that word and check if that word is removed
        DictionaryWord dictionaryWord = mDatabase.dictionaryWordDAO().getDictionaryWord(mWord.getWord());
        assertNull(dictionaryWord);


        // getting the dictionary Words from database
        List<DictionaryWord> loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // make sure that rest of the words are left
        assertThat(loadedTasks.size(), is(2));
    }

    @Test
    public void test9_deleteAllDictionaryWords(){

        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task2", 25, true));
        mDatabase.dictionaryWordDAO().insert(new DictionaryWord("task3", 8, true));

        // getting the dictionary Words from database
        List<DictionaryWord> loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // make sure that there are 3 words in the database
        assertThat(loadedTasks.size(), is(3));

        // delete all words
        mDatabase.dictionaryWordDAO().deleteAll();

        // getting the dictionary Words from database
        loadedTasks = mDatabase.dictionaryWordDAO().getAllDictionaryWords();

        // check that all the words are removed successfully
        assertThat(loadedTasks.size(), is(0));

    }



    private void assertWordsDeactivated(List<DictionaryWord> loadedTasks) {
        for (DictionaryWord word : loadedTasks) {
            assertTrue(!word.isActive());
        }
    }

    private void assertWordsOrder(List<DictionaryWord> loadedTasks) {
        for (int i = 0; i < loadedTasks.size(); i++) {
            for (int j = i + 1; j < loadedTasks.size() - (i+1); j++) {
                assertTrue(loadedTasks.get(i).getFrequency() >= loadedTasks.get(j).getFrequency());
            }
        }
    }

    private void assertWord(DictionaryWord loaded, String word, int frequency, boolean isActive) {
        assertThat(loaded, notNullValue());
        assertThat(loaded.getWord(), is(word));
        assertThat(loaded.getFrequency(), is(frequency));
        assertThat(loaded.isActive(), is(isActive));
    }

    private void assertWord(DictionaryWord loaded, DictionaryWord mWord) {
        assertThat(loaded, notNullValue());
        assertThat(loaded.getWord(), is(mWord.getWord()));
        assertThat(loaded.getFrequency(), is(mWord.getFrequency()));
        assertThat(loaded.isActive(), is(mWord.isActive()));
    }


    @After
    public void tearDown() throws Exception {
        mDatabase.close();
    }


}