package com.example.heathwellnessassistant.DataLayer;

import android.app.Application;

import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;

public class AppDataProvider {

    public static JournalEntryDao getJournalEntryDao(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        return db.journalEntryDao();
    }

    // Later you can add more: UserDao, SettingsDao, etc.
}