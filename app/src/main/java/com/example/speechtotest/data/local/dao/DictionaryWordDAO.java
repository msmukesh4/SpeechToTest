package com.example.speechtotest.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.speechtotest.data.model.DictionaryWord;

import java.util.List;

/**
 * Created by mukesh on 04/02/19
 */
@Dao
public interface DictionaryWordDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DictionaryWord word);

    @Query("Delete From dictionary_words")
    void deleteAll();

    @Query("Delete From dictionary_words WHERE word = :word")
    void delete(String word);

    @Query("Select * From dictionary_words Order by frequency DESC")
    List<DictionaryWord> getAllDictionaryWords();

    @Query("Select * From dictionary_words WHERE word = :word")
    DictionaryWord getDictionaryWord(String word);

    @Update
    void update(DictionaryWord word);

    @Query("UPDATE dictionary_words SET is_active = :isActive WHERE word = :word")
    void active(boolean isActive, String word);

    @Query("UPDATE dictionary_words SET is_active = :isActive, frequency = :frequency WHERE word = :word")
    void update(boolean isActive, int frequency, String word);

    @Query("UPDATE dictionary_words SET is_active = :isActive WHERE is_active = :condition")
    void resetActiveWords(boolean isActive, boolean condition);
}
