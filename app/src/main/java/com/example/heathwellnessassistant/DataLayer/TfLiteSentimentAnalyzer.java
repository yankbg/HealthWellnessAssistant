package com.example.heathwellnessassistant.DataLayer;

import android.content.Context;


import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;




public class TfLiteSentimentAnalyzer {
    private static final String MODEL_FILE = "emotion_model.tflite";
    private final NLClassifier classifier;


    public TfLiteSentimentAnalyzer(Context context) {
        try{
            NLClassifier.NLClassifierOptions options =
                    NLClassifier.NLClassifierOptions.builder()
                            .build();

            classifier = NLClassifier.createFromFileAndOptions(
                    context.getApplicationContext(),
                    MODEL_FILE,
                    options
            );
        }catch (Exception e){
            throw new IllegalStateException("Failed to load sentiment model", e);
        }
    }
}
