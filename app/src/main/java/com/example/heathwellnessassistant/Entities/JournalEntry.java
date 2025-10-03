package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "JournalEntry")

public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private long journal_id;

    String text_context;
    long date;
    String ai_sentiment;
    float ai_confidence;

    public JournalEntry() {
    }

    public JournalEntry(String text_context, long date, String ai_sentiment, float ai_confidence) {
        this.text_context = text_context;
        this.date = date;
        this.ai_sentiment = ai_sentiment;
        this.ai_confidence = ai_confidence;
    }

    public String getText_context() {
        return text_context;
    }

    public void setText_context(String text_context) {
        this.text_context = text_context;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAi_sentiment() {
        return ai_sentiment;
    }

    public void setAi_sentiment(String ai_sentiment) {
        this.ai_sentiment = ai_sentiment;
    }

    public float getAi_confidence() {
        return ai_confidence;
    }

    public void setAi_confidence(float ai_confidence) {
        this.ai_confidence = ai_confidence;
    }
}
