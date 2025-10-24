package com.example.heathwellnessassistant.Database;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface JournalEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(JournalEntry journalEntry);
    @Update
    public void update(JournalEntry journalEntry);

    @Query("SELECT * FROM JournalEntry ORDER BY date ASC")
    public List<JournalEntry> getJournalEntry();

    @Query("SELECT * FROM JournalEntry WHERE journal_id = :id ORDER BY date ASC")
    public JournalEntry getResult(long id);

    @Query("SELECT * from JournalEntry ORDER BY date ASC")
    LiveData<List<JournalEntry>> getAllJournalEntry();

    @Delete
    public void deleteJournalEntry(JournalEntry journalEntry);
}
