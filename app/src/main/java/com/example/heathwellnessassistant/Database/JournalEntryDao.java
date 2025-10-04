package com.example.heathwellnessassistant.Database;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.sql.Date;
import java.util.List;

@Dao
public interface JournalEntryDao {

    @Insert
    public void inserttextanddate(String textContext, Date date);

    @Insert
    public void insertallfield(String textContext, Date date, String ai_sentiment, float ai_confidence);


    @Query("SELECT * FROM JournalEntry")
    public List<JournalEntry> getJournalEntry();

    @Query("SELECT ai_confidence, ai_sentiment FROM JournalEntry WHERE journal_id = :id")
    public JournalEntry getResult(int id);
}
