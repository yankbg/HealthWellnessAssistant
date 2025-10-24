package com.example.heathwellnessassistant;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private JournalEntryDao journalEntryDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        journalEntryDao = db.journalEntryDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setText_context("Test journal entry");
        journalEntry.setDate(new Date());
        journalEntryDao.insert(journalEntry);
        JournalEntry byId = journalEntryDao.getResult(1);
        assertEquals(byId.getText_context(), "Test journal entry");
    }

    @Test
    public void deleteUser() throws Exception {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setText_context("Test journal entry");
        journalEntry.setDate(new Date());
        journalEntryDao.insert(journalEntry);
        JournalEntry byId = journalEntryDao.getResult(1);
        assertEquals(byId.getText_context(), "Test journal entry");
//        journalEntryDao.delete(byId);
//        JournalEntry deleted = journalEntryDao.findById(1);
//        assertNull(deleted);
    }
}
