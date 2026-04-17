package com.example.heathwellnessassistant.DataLayer;

import com.example.heathwellnessassistant.Entities.JournalEntry;

public interface OnJournalFeedbackListener {
    void onCorrectClick(JournalEntry entry);
    void onWrongClick(JournalEntry entry);
}
