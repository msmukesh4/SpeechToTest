package com.example.speechtotest.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.speechtotest.R;
import com.example.speechtotest.data.source.model.DictionaryWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukesh on 30/01/19
 * RecyclerView Adpater for Showing dictionary words
 */
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {

    @VisibleForTesting
    private List<DictionaryWord> words;

    private Context context;

    public DictionaryAdapter(Context context){
        this.context = context;
        words = new ArrayList<>();
    }

    @NonNull
    @Override
    public DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dictionary_view_item, viewGroup, false);

        return new DictionaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryViewHolder dictionaryViewHolder, int i) {
        DictionaryWord word = words.get(i);
        dictionaryViewHolder.word.setText(word.getWord());
        dictionaryViewHolder.frequency.setText(String.valueOf(word.getFrequency()));
        dictionaryViewHolder.llListItem.setSelected(word.isActive());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


    /**
     * update the word list when the list of dictionary words changes
     * and update the UI accordingly using notifyDataSetChanged()
     * @param words
     */
    public void setWords(List<DictionaryWord> words){
        this.words = words;
        notifyDataSetChanged();
    }


    /**
     * ViewHolder for DictionaryAdapter
     */
    public class DictionaryViewHolder extends RecyclerView.ViewHolder {

        public TextView word, frequency;
        public LinearLayout llListItem;

        public DictionaryViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.tv_word);
            frequency = itemView.findViewById(R.id.tv_frequency);
            llListItem = itemView.findViewById(R.id.ll_list_item);
        }
    }
}
