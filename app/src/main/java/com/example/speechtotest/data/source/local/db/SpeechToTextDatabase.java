package com.example.speechtotest.data.source.local.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.speechtotest.data.source.local.dao.DictionaryWordDAO;
import com.example.speechtotest.data.source.model.DictionaryWord;

/**
 * Created by mukesh on 04/02/19
 * making the database class as singleton
 */
@Database(entities = {DictionaryWord.class}, version = 1, exportSchema = false)
public abstract class SpeechToTextDatabase extends RoomDatabase {

    private static SpeechToTextDatabase INSTANCE;

    private static final String TAG = SpeechToTextDatabase.class.getSimpleName();

    public static SpeechToTextDatabase getInstance(final Context context){
        Log.e(TAG, "getInstance:");
        if (INSTANCE == null){
            synchronized (SpeechToTextDatabase.class){
                if (INSTANCE == null){

                    // Create DB here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpeechToTextDatabase.class,"speech_to_text.db")
                            .addCallback(roomDBCallback)
                            .build();

                }
            }
        }

        return INSTANCE;
    }

    public abstract DictionaryWordDAO dictionaryWordDAO();

    public static RoomDatabase.Callback roomDBCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "onOpen:");
        }
    };
}
