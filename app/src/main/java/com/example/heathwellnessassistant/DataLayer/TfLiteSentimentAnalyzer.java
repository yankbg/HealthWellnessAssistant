package com.example.heathwellnessassistant.DataLayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.flex.FlexDelegate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class TfLiteSentimentAnalyzer {

    private static final String TAG = "TfLiteAnalyzer";
    private static final int MAX_SEQ_LEN = 100;
    private static final int UNK_TOKEN = 1;

    private Interpreter interpreter;
    private Map<String, Integer> vocab = new HashMap<>();

    private final String[] labels = {"sadness", "joy", "love", "anger", "fear", "surprise"};
    private static final Pattern STRIP_PUNCT =
            Pattern.compile("[!\"#$%&()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~']");

    public TfLiteSentimentAnalyzer(Context context) {
        try {
            FlexDelegate flexDelegate = new FlexDelegate();
            Interpreter.Options options = new Interpreter.Options().addDelegate(flexDelegate);
            interpreter = new Interpreter(loadModelFile(context, "emotion_model_v2.tflite"), options);
            loadVocab(context);
            Log.d(TAG, "Initialized OK. Vocab size: " + vocab.size());
        } catch (Exception e) {
            Log.e(TAG, "Init failed", e);
        }
    }

    private MappedByteBuffer loadModelFile(Context context, String filename) throws Exception {
        AssetFileDescriptor fd = context.getAssets().openFd(filename);
        FileInputStream stream = new FileInputStream(fd.getFileDescriptor());
        FileChannel channel = stream.getChannel();
        return channel.map(FileChannel.MapMode.READ_ONLY, fd.getStartOffset(), fd.getDeclaredLength());
    }

    private void loadVocab(Context context) throws Exception {
        InputStream is = context.getAssets().open("vocab.json");
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();
        JSONObject json = new JSONObject(new String(buffer));
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String word = keys.next();
            vocab.put(word, json.getInt(word));
        }
    }

    /** Replicates the Python TextVectorization preprocessing exactly */
    private int[] tokenize(String text) {
        // 1. Lowercase
        text = text.toLowerCase();
        // 2. Strip punctuation (same regex as the model's TextVectorization layer)
        text = STRIP_PUNCT.matcher(text).replaceAll("");
        // 3. Split on whitespace
        String[] words = text.trim().split("\\s+");
        // 4. Map to token ids, pad/truncate to MAX_SEQ_LEN
        int[] tokens = new int[MAX_SEQ_LEN]; // defaults to 0 (padding)
        for (int i = 0; i < Math.min(words.length, MAX_SEQ_LEN); i++) {
            Integer idx = vocab.get(words[i]);
            tokens[i] = (idx != null) ? idx : UNK_TOKEN;
        }
        return tokens;
    }

    public MyResult analyzeText(String text) {
        if (interpreter == null) {
            Log.e(TAG, "Interpreter is null");
            return new MyResult("sadness", 0.0f);
        }
        try {
            // Input: int[1][100]
            int[][] input = new int[1][MAX_SEQ_LEN];
            int[] tokens = tokenize(text);
            System.arraycopy(tokens, 0, input[0], 0, MAX_SEQ_LEN);

            // Output: float[1][6]
            float[][] output = new float[1][6];

            interpreter.run(input, output);

            int maxIdx = 0;
            float maxProb = -1f;
            for (int i = 0; i < output[0].length; i++) {
                if (output[0][i] > maxProb) {
                    maxProb = output[0][i];
                    maxIdx = i;
                }
            }
            Log.d(TAG, "Result: " + labels[maxIdx] + " @ " + (maxProb * 100) + "%");
            return new MyResult(labels[maxIdx], maxProb);

        } catch (Exception e) {
            Log.e(TAG, "Inference failed", e);
            return new MyResult("sadness", 0.0f);
        }
    }

    public void close() {
        if (interpreter != null) interpreter.close();
    }
}