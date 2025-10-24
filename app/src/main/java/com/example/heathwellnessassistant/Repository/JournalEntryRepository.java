package com.example.heathwellnessassistant.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.util.List;

public class JournalEntryRepository {
    private JournalEntryDao journalEntryDao;
    private LiveData<List<JournalEntry>> journalEntryLiveData;

    public JournalEntryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        journalEntryDao = db.journalEntryDao();
        journalEntryLiveData = journalEntryDao.getAllJournalEntry();
    }

    public LiveData<List<JournalEntry>> getJournalEntryLiveData() {
        return journalEntryLiveData;
    }
    public void insertJournalEntry(JournalEntry journalEntry){
        new InsertAsyncTask(journalEntryDao).execute(journalEntry);
    }
    public void deleteJournalEntry(JournalEntry journalEntry) {
        new DeleteAsyncTask(journalEntryDao).execute(journalEntry);
    }
    // Asynchronous tasks for database operations
    private static class InsertAsyncTask extends AsyncTask<JournalEntry, Void, Void> {
        private JournalEntryDao asyncTaskDao;

        InsertAsyncTask(JournalEntryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JournalEntry... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<JournalEntry, Void, Void> {
        private JournalEntryDao asyncTaskDao;

        DeleteAsyncTask(JournalEntryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JournalEntry... params) {
            asyncTaskDao.deleteJournalEntry(params[0]);
            return null;
        }
    }
}
