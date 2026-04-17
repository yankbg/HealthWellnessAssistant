package com.example.heathwellnessassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.Repository.JournalEntryRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalViewModel extends ViewModel {
    private JournalEntryRepository repository;
    private LiveData<List<JournalEntry>> journalEntryAll;
    private final Executor executorService;
    public JournalViewModel(@NonNull JournalEntryRepository repository){

        super();
        this.repository = repository;
        journalEntryAll = repository.getJournalEntryLiveData();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    //Expose data: return LiveData<List<JournalEntry>> from the repository
    public LiveData<List<JournalEntry>> getJournalEntryAll() {
        return journalEntryAll;
    }

    //Forward actions: call analyzeAndSaveEntry from the repository
    public void saveEntry(String text) {
        
        repository.analyzeAndSaveEntry(text);
    }
    public void markCorrect(long id) {
        executorService.execute(() -> repository.updateIsCorrect(id, true));
    }
    public void markWrong(long id){
        executorService.execute(() -> repository.updateIsCorrect(id, false));
    }
    public void updateCorrectedEmotion(long id, String correctedEmotion){
        executorService.execute(() -> repository.updateCorrectedEmotion(id, correctedEmotion));
    }

}
