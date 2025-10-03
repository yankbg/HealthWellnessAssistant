package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SleepSession")
public class SleepSession {

    @PrimaryKey(autoGenerate = true)
    private long sleep_id;

    long start_time;
    long end_time;
    float sensor_data_summary;
    int Sleep_quality_score;
    int durationMinute;

    public SleepSession() {
    }

    public SleepSession(long start_time, long end_time, float sensor_data_summary, int sleep_quality_score, int durationMinute) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.sensor_data_summary = sensor_data_summary;
        Sleep_quality_score = sleep_quality_score;
        this.durationMinute = durationMinute;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public float getSensor_data_summary() {
        return sensor_data_summary;
    }

    public void setSensor_data_summary(float sensor_data_summary) {
        this.sensor_data_summary = sensor_data_summary;
    }

    public int getSleep_quality_score() {
        return Sleep_quality_score;
    }

    public void setSleep_quality_score(int sleep_quality_score) {
        Sleep_quality_score = sleep_quality_score;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
    }
}
