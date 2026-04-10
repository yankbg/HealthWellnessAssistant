package com.example.heathwellnessassistant.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.heathwellnessassistant.DataLayer.MyResult;
import com.example.heathwellnessassistant.DataLayer.TfLiteSentimentAnalyzer;
import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.lang.reflect.Executable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalEntryRepository {
    private JournalEntryDao journalEntryDao;
    private LiveData<List<JournalEntry>> journalEntryLiveData;
    private final TfLiteSentimentAnalyzer sentimentAnalyzer;
    private final Executor executor;


    public JournalEntryRepository(Application application, TfLiteSentimentAnalyzer sentimentAnalyzer) {
        AppDatabase db = AppDatabase.getInstance(application);
        journalEntryDao = db.journalEntryDao();
        journalEntryLiveData = journalEntryDao.getAllJournalEntry();
        this.sentimentAnalyzer = sentimentAnalyzer;
        this.executor = Executors.newSingleThreadExecutor();
    }
    public void analyzeAndSaveEntry(String text){
        if(text == null || text.trim().isEmpty()){
            return;
        }
        executor.execute(() ->{
            // Run AI on background thread
            MyResult aiResult = sentimentAnalyzer.analyzeText(text);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = calendar.getTime();

            //Create entry
            JournalEntry entry = new JournalEntry();
            entry.setText_context(text);
            entry.setAi_sentiment(aiResult.getMessage());
            entry.setAi_confidence(aiResult.getValue());
            entry.setDate(date);

            //Save to Room
            journalEntryDao.insert(entry);


        });
    }

    public LiveData<List<JournalEntry>> getJournalEntryLiveData() {
        return journalEntryLiveData;
    }
    public void insertJournalEntry(JournalEntry journalEntry){
        executor.execute(() -> {
            journalEntryDao.insert(journalEntry);
        });
    }
    public void deleteJournalEntry(JournalEntry journalEntry) {
        executor.execute(() -> {
            journalEntryDao.deleteJournalEntry(journalEntry);
        });
    }

}
