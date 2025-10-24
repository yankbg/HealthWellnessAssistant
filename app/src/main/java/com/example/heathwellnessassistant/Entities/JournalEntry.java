package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;


@Entity(tableName = "JournalEntry")

public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private long journal_id;

   private String text_context;
    private Date date;
    private String ai_sentiment;
    private float ai_confidence;

    public JournalEntry() {
    }

    public JournalEntry(String text_context, Date date, String ai_sentiment, float ai_confidence) {
        this.text_context = text_context;
        this.date = date;
        this.ai_sentiment = ai_sentiment;
        this.ai_confidence = ai_confidence;
    }

    public JournalEntry(long journal_id, String text_context, Date date, String ai_sentiment, float ai_confidence) {
        this.journal_id = journal_id;
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

    public float getAi_confidence() {
        return ai_confidence;
    }

    public void setAi_confidence(float ai_confidence) {
        this.ai_confidence = ai_confidence;
    }

    public long getJournal_id() {
        return journal_id;
    }

    public void setJournal_id(long journal_id) {
        this.journal_id = journal_id;
    }

    //equals and hashcode method for Junit testing

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntry that = (JournalEntry) o;
        final float CONFIDENCE_TOLERANCE = 0.00001f;

        // Compare all fields based on their content
        return Math.abs(that.ai_confidence - ai_confidence) < CONFIDENCE_TOLERANCE&&
                Objects.equals(text_context, that.text_context) &&
                Objects.equals(date, that.date) &&
                Objects.equals(ai_sentiment, that.ai_sentiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text_context, date, ai_sentiment, ai_confidence);
    }
}
