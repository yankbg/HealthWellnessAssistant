package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "JournalEntry")

public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String text_context;
    Date date;
    String ai_sentiment;

    public JournalEntry() {
    }

    public JournalEntry(String text_context, Date date, String ai_sentiment) {
        this.text_context = text_context;
        this.date = date;
        this.ai_sentiment = ai_sentiment;
    }

    public String getText_context() {
        return text_context;
    }

    public void setText_context(String text_context) {
        this.text_context = text_context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAi_sentiment() {
        return ai_sentiment;
    }

    public void setAi_sentiment(String ai_sentiment) {
        this.ai_sentiment = ai_sentiment;
    }
}
