package com.example.heathwellnessassistant.DataLayer;

public class MyResult {
    float value;
    String message;

    MyResult( String message,float value) {
        this.value = value;
        this.message = message;
    }

    public float getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
