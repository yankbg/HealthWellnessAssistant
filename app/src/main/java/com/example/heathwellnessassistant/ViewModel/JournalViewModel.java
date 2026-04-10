package com.example.heathwellnessassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.Repository.JournalEntryRepository;

import java.util.List;

public class JournalViewModel extends ViewModel {
    private JournalEntryRepository repository;
    private LiveData<List<JournalEntry>> journalEntryAll;
    public JournalViewModel(@NonNull JournalEntryRepository repository){

        super();
        this.repository = repository;
        journalEntryAll = repository.getJournalEntryLiveData();
    }

    //Expose data: return LiveData<List<JournalEntry>> from the repository
    public LiveData<List<JournalEntry>> getJournalEntryAll() {
        return journalEntryAll;
    }

    //Forward actions: call analyzeAndSaveEntry from the repository
    public void saveEntry(String text) {
        
        repository.analyzeAndSaveEntry(text);
    }
}
