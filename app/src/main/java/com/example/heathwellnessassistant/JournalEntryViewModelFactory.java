package com.example.heathwellnessassistant;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.heathwellnessassistant.Repository.JournalEntryRepository;
import com.example.heathwellnessassistant.ViewModel.JournalViewModel;

public class JournalEntryViewModelFactory implements ViewModelProvider.Factory{
    private final JournalEntryRepository repository;

    public JournalEntryViewModelFactory(JournalEntryRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == JournalViewModel.class) {
            return (T) new JournalViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }
}
