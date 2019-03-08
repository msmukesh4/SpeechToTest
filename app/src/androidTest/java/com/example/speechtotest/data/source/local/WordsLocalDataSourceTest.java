package com.example.speechtotest.data.source.local;

import android.arch.persistence.room.Room;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.speechtotest.data.WordsDataSource;
import com.example.speechtotest.data.source.local.db.SpeechToTextDatabase;
import com.example.speechtotest.data.source.model.DictionaryWord;
import com.example.speechtotest.ui.home.HomeActivity;
import com.example.speechtotest.util.SingleExecutors;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by mukesh on 08/03/19
 *
 * Integration test for the {@link WordsDataSource}.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WordsLocalDataSourceTest {

    // this rule will launch the activity and also give us the reference of the activity
    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivity = new ActivityTestRule<>(HomeActivity.class);


    private SpeechToTextDatabase mDatabase = null;
    private WordsDataSource mLocalDataSource = null;

    @Before
    public void setUp() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(mHomeActivity.getActivity().getApplicationContext(),
                SpeechToTextDatabase.class).build();
        mLocalDataSource = new WordsLocalDataSource(new SingleExecutors(), mDatabase);
    }

    @Test
    public void test1_preConditions(){
        assertNotNull(mDatabase);
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void test2_saveWord_retrievesWord(){

        // save a given word
        final DictionaryWord word = new DictionaryWord("hello", 20, false);
        mLocalDataSource.saveWord(word);

        // retrieve the word from DB
        mLocalDataSource.getWord("hello", new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {
                assertWord(dictionaryWord, word);
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback Error");
            }
        });
    }

    @Test
    public void test3_activateWord_retrieveWordIsActivated(){

        // save a given word
        final DictionaryWord word = new DictionaryWord("hello", 20, false);
        mLocalDataSource.saveWord(word);

        // activate word
        mLocalDataSource.activateWord("hello");

        // retrieve the word from DB and test for activated
        mLocalDataSource.getWord("hello", new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {
                assertTrue(dictionaryWord.isActive());
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback Error");
            }
        });
    }

    @Test
    public void test4_updateWord_retrieveWordIsUpdated(){
        // save a given word
        final DictionaryWord word = new DictionaryWord("hello", 20, false);
        mLocalDataSource.saveWord(word);

        // create another word
        final DictionaryWord tempWord = new DictionaryWord("hello", 21, true);

        // update word
        mLocalDataSource.activateWord(tempWord);

        // retrieve the word from DB and test that it's updated
        mLocalDataSource.getWord("hello", new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {
                assertTrue(dictionaryWord.isActive());
                assertNotEquals(dictionaryWord.getFrequency(), word.getFrequency());
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback Error");
            }
        });
    }

    @Test
    public void test5_resetWords_retrieveWordsAllDeactivated(){
        // save all given words
        final DictionaryWord word1 = new DictionaryWord("test1", 20, true);
        final DictionaryWord word2 = new DictionaryWord("test2", 20, true);
        final DictionaryWord word3 = new DictionaryWord("test3", 20, false);
        mLocalDataSource.saveWord(word1);
        mLocalDataSource.saveWord(word2);
        mLocalDataSource.saveWord(word3);

        // reset all words
        mLocalDataSource.resetAllWords();

        // get all words from DB and check all the words are deactivated
        mLocalDataSource.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<DictionaryWord> wordList) {
                assertAllWordsDeactivated(wordList);
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback Error");
            }
        });
    }

    @Test
    public void test6_deleteAWord_retrieveThatWord(){
        // save all given words
        final DictionaryWord word1 = new DictionaryWord("test1", 20, true);
        final DictionaryWord word2 = new DictionaryWord("test2", 20, true);
        final DictionaryWord word3 = new DictionaryWord("test3", 20, false);
        mLocalDataSource.saveWord(word1);
        mLocalDataSource.saveWord(word2);
        mLocalDataSource.saveWord(word3);

        // delete the first word
        mLocalDataSource.deleteWord(word1);

        // try to retrieve that task
        mLocalDataSource.getWord(word1.getWord(), new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(DictionaryWord dictionaryWord) {
                assertNull(dictionaryWord);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });


        // get all words from DB and check the size is 2
        mLocalDataSource.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<DictionaryWord> wordList) {
                assertThat(wordList.size(), is(2));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback Error");
            }
        });
    }

    @Test
    public void test7_deleteAllWords_retrieveWords(){
        // save all given words
        final DictionaryWord word1 = new DictionaryWord("test1", 20, true);
        final DictionaryWord word2 = new DictionaryWord("test2", 20, true);
        final DictionaryWord word3 = new DictionaryWord("test3", 20, false);
        mLocalDataSource.saveWord(word1);
        mLocalDataSource.saveWord(word2);
        mLocalDataSource.saveWord(word3);

        // delete all words
        mLocalDataSource.deleteAllWords();

        mLocalDataSource.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<DictionaryWord> wordList) {
                assertThat(wordList.size(), is(0));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void assertAllWordsDeactivated(List<DictionaryWord> wordList) {
        for (DictionaryWord word : wordList) {
            assertFalse(word.isActive());
        }
    }

    private void assertWord(DictionaryWord loaded, DictionaryWord mWord) {
        assertThat(loaded, notNullValue());
        assertThat(loaded.getWord(), CoreMatchers.is(mWord.getWord()));
        assertThat(loaded.getFrequency(), CoreMatchers.is(mWord.getFrequency()));
        assertThat(loaded.isActive(), CoreMatchers.is(mWord.isActive()));
    }


    @After
    public void tearDown(){
        mDatabase.close();
        mLocalDataSource = null;
    }

}