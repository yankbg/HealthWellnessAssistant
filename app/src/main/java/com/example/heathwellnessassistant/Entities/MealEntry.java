package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "MealEntry")
public class MealEntry {

    @PrimaryKey(autoGenerate = true)
    private int meal_id;

    String image_uri;
    String ai_food_name;
    String ai_calories;
    Date date;

    public MealEntry() {
    }

    public MealEntry(String image_uri, String ai_food_name, String ai_calories, Date date) {
        this.image_uri = image_uri;
        this.ai_food_name = ai_food_name;
        this.ai_calories = ai_calories;
        this.date = date;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getAi_food_name() {
        return ai_food_name;
    }

    public void setAi_food_name(String ai_food_name) {
        this.ai_food_name = ai_food_name;
    }

    public String getAi_calories() {
        return ai_calories;
    }

    public void setAi_calories(String ai_calories) {
        this.ai_calories = ai_calories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
