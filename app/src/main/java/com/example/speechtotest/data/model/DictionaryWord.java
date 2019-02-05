package com.example.speechtotest.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.speechtotest.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mukesh on 30/01/19
 */
@Entity(tableName = "dictionary_words")
public class DictionaryWord {

//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    public final int id;

    @NonNull
    @ColumnInfo(name = "word")
    @PrimaryKey
    @SerializedName("word")
    String word;

    @NonNull
    @ColumnInfo(name = "frequency")
    @SerializedName("frequency")
    int frequency;

    @ColumnInfo(name = "is_active")
    boolean active = false;

    public DictionaryWord(@NonNull String word, @NonNull int frequency, boolean active) {
        this.word = word;
        this.frequency = frequency;
        this.active = active;
    }

    @Override
    public String toString() {
        return "DictionaryWord{" +
                "word='" + word + '\'' +
                ", frequency='" + frequency + '\'' +
                ", active=" + active +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

}
