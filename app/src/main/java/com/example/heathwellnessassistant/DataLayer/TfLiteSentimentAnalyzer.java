package com.example.heathwellnessassistant.DataLayer;

import android.content.Context;
import android.util.Log;


import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.util.List;


public class TfLiteSentimentAnalyzer {
    private static final String MODEL_FILE = "emotion_model.tflite";
    private final NLClassifier classifier;
    private static final String TAG = "TfLiteSentimentAnalyzer";


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
    public MyResult analyzeText(String text){
        if (text == null || text.trim().isEmpty()){
            return new MyResult(0,"Empty input text");
        }
        try{
            // 1. Run inference: get all categories (emotions)
            List<Category> results = classifier.classify(text);

            if(results == null || results.isEmpty()){
                return new MyResult(1, "Unknown");
            }
            // 2. Take the top‑scoring class (ArgMax)
            Category top = results.get(0);
            String label = top.getLabel();
            float confidence = top.getScore();

            // 3. Format: "joy with 85.0% confidence"
//            return String.format(
//                    "%s with %.1f%% confidence",
//                    label,
//                    confidence * 100
//            );
            return new MyResult(confidence,label);

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "analyzeText: "+e);

            return new MyResult(2, "Error");
        }
    }
}
