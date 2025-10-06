package com.example.heathwellnessassistant.Database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.heathwellnessassistant.Entities.JournalEntry;

import kotlin.jvm.Volatile;

@Database(entities = {JournalEntry.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract JournalEntryDao journalEntryDao();

    @Volatile
    private static  volatile AppDatabase INSTANCE; // Use volatile for thread safety
    private static final Object LOCK = new Object(); // Lock object for synchronization

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (LOCK) { // Synchronize to prevent multiple threads from creating instances
                if (INSTANCE == null) { // Double-check locking
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "HeathWellnessDb")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
