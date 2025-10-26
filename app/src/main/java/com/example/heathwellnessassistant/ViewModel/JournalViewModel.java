package com.example.heathwellnessassistant.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.Repository.JournalEntryRepository;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalEntryRepository myrepo;
    private LiveData<List<JournalEntry>> journalEntryAll;
    public JournalViewModel(Application application){

        super(application);
        myrepo = new JournalEntryRepository(application);
        journalEntryAll = myrepo.getJournalEntryLiveData();
    }

    public LiveData<List<JournalEntry>> getJournalEntryAll() {
        return journalEntryAll;
    }
    public void insetJournalEntry(JournalEntry journalEntry){myrepo.insertJournalEntry(journalEntry);}
}
