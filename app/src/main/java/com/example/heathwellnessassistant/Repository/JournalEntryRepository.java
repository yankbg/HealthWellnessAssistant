package com.example.heathwellnessassistant.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.heathwellnessassistant.DataLayer.MyResult;
import com.example.heathwellnessassistant.DataLayer.TfLiteSentimentAnalyzer;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.text.SimpleDateFormat;
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


    public JournalEntryRepository(JournalEntryDao journalEntryDao, TfLiteSentimentAnalyzer sentimentAnalyzer) {
//        AppDatabase db = AppDatabase.getInstance(application);
        this.journalEntryDao = journalEntryDao;
        journalEntryLiveData = journalEntryDao.getAllJournalEntry();
        this.sentimentAnalyzer = sentimentAnalyzer;
        this.executor = Executors.newSingleThreadExecutor();
    }
    public void updateIsCorrect(long id, boolean isCorrect){
        journalEntryDao.updateIsCorrect(id, isCorrect);
    }
    public void updateCorrectedEmotion(long id, String correctedEmotion){
        journalEntryDao.updatecorrectedEmotion(id, correctedEmotion);
    }
    public void analyzeAndSaveEntry(String text){
        if(text == null || text.trim().isEmpty()){
            return;
        }
        executor.execute(() ->{
            if (sentimentAnalyzer == null) {
                Log.w("AI_ERROR", "analyzeAndSaveEntry skipped: AI model not loaded");
                return;
            }
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
