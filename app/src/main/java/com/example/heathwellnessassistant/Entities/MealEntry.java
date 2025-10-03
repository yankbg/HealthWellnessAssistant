package com.example.heathwellnessassistant.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "MealEntry")
public class MealEntry {

    @PrimaryKey(autoGenerate = true)
    private long meal_id;

    String image_uri;
    String ai_food_name;
    float ai_calories;
    Date date;
    String ai_bounding_box;

    public MealEntry() {
    }

    public MealEntry(String image_uri, String ai_food_name, float ai_calories, Date date, String ai_bounding_box) {
        this.image_uri = image_uri;
        this.ai_food_name = ai_food_name;
        this.ai_calories = ai_calories;
        this.date = date;
        this.ai_bounding_box = ai_bounding_box;
    }

    public String getAi_bounding_box() {
        return ai_bounding_box;
    }

    public void setAi_bounding_box(String ai_bounding_box) {
        this.ai_bounding_box = ai_bounding_box;
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

    public float getAi_calories() {
        return ai_calories;
    }

    public void setAi_calories(float ai_calories) {
        this.ai_calories = ai_calories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
