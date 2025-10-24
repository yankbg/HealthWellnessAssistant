package com.example.heathwellnessassistant.Database;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface JournalEntryDao {

    @Insert
    public void insert(JournalEntry journalEntry);



    @Query("SELECT * FROM JournalEntry")
    public List<JournalEntry> getJournalEntry();

    @Query("SELECT * FROM JournalEntry WHERE journal_id = :id")
    public JournalEntry getResult(long id);
}
