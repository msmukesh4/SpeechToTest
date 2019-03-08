package com.example.speechtotest.fake_data_source;

import com.example.speechtotest.data.source.model.DictionaryWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukesh on 08/03/19
 */
public class FakeDictionaryWords {

    private List<DictionaryWord> dictionaryWords = new ArrayList<>();


    public FakeDictionaryWords(DictionaryWord word){
        dictionaryWords.add(word);
        dictionaryWords.add(new DictionaryWord("word2", 66, false));
        dictionaryWords.add(new DictionaryWord("word3", 2, false));
    }

    public List<DictionaryWord> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(List<DictionaryWord> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }
}
