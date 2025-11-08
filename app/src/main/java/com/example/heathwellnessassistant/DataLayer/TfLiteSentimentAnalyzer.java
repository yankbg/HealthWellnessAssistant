package com.example.heathwellnessassistant.DataLayer;

import android.content.Context;

import org.tensorflow.lite.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class TfLiteSentimentAnalyzer {
    private static final String modelFile = "emotion_model.tflite";
    private Interpreter interpreter;
    private final Map<Integer, String> labels;

//    public TfLiteSentimentAnalyzer() {
//    }

    public TfLiteSentimentAnalyzer(Context context) {
        labels = new HashMap<>();
        labels.put(0, "sadness");
        labels.put(1, "joy");
        labels.put(2, "love");
        labels.put(3, "anger");
        labels.put(4, "fear");
        labels.put(5, "surprise");
    }
}
