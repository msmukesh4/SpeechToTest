package com.example.speechtotest.data.api.response;

import com.example.speechtotest.data.model.DictionaryWord;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mukesh on 30/01/19
 */
public class DictionaryAPIResponse implements Serializable {

    @SerializedName("dictionary") ArrayList<DictionaryWord> dictionaryWord;

    public ArrayList<DictionaryWord> getDictionaryWord() {
        return dictionaryWord;
    }

    public void setDictionaryWord(ArrayList<DictionaryWord> dictionaryWord) {
        this.dictionaryWord = dictionaryWord;
    }

    @Override
    public String toString() {
        return "DictionaryAPIResponse{" +
                "dictionary=" + dictionaryWord +
                '}';
    }
}
