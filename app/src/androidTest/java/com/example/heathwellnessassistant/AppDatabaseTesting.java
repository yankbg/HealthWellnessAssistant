package com.example.heathwellnessassistant;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;

import java.util.Date;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppDatabaseTesting {
    private AppDatabase database;
    private JournalEntryDao journalEntryDao;
    Date date = new Date(1672531200000L);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.heathwellnessassistant", appContext.getPackageName());
    }
    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        // Use inMemoryDatabaseBuilder to create a temporary DB for testing
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries() // For testing only
                .build();
        journalEntryDao = database.journalEntryDao();
    }
    @After
    public void tearDown() {
        database.close();
    }
    @Test
    public void testInsertAndRead() {
        // Arrange: create an entity and insert it
        JournalEntry entity = new JournalEntry("Hello World", date,"Happy",7.8f/* initialize fields */);
        journalEntryDao.insert(entity);
        long id = entity.getJournal_id();
        // Act: fetch the entity
        JournalEntry fetched = journalEntryDao.getResult(id);

        // Assert: verify the data is correctly saved and fetched
        assertEquals(entity.getAi_confidence(), fetched.getAi_confidence(), 0.001f);
        assertEquals(entity.getAi_sentiment(), fetched.getAi_sentiment());
    }
    @Test
    public void testGetResultWithInsertedEntry() {
        JournalEntry entry = new JournalEntry("Mood entry", new Date(System.currentTimeMillis()), "neutral", 0.60f);
        journalEntryDao.insert(entry);
        List<JournalEntry> entries = journalEntryDao.getJournalEntry();
        long id = entries.get(0).getJournal_id();

        // This will fail if the SELECT in getResult does not match the JournalEntry object
        JournalEntry result = journalEntryDao.getResult(id);

        assertEquals("Mood entry", result.getText_context());
        assertEquals(0.60f, result.getAi_confidence(), 0.0001f);
    }
}