package com.example.heathwellnessassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathwellnessassistant.Adapters.JournalEntryAdapter;
import com.example.heathwellnessassistant.DataLayer.OnJournalFeedbackListener;
import com.example.heathwellnessassistant.DataLayer.TfLiteSentimentAnalyzer;
import com.example.heathwellnessassistant.Database.AppDatabase;
import com.example.heathwellnessassistant.Database.JournalEntryDao;
import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.Repository.JournalEntryRepository;
import com.example.heathwellnessassistant.ViewModel.JournalViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private JournalViewModel viewModel;
    private RecyclerView recyclerView;
    private JournalEntryAdapter adapter;
    TfLiteSentimentAnalyzer analyzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Build dependencies
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        JournalEntryDao dao = db.journalEntryDao();
        try {
            analyzer = new TfLiteSentimentAnalyzer(this);
            JournalEntryRepository repository = new JournalEntryRepository(dao, analyzer);

            //Create ViewModel with Factory
            JournalEntryViewModelFactory factory = new JournalEntryViewModelFactory(repository);
            this.viewModel = new ViewModelProvider(this, factory).get(JournalViewModel.class);

            //Observe data
            this.viewModel.getJournalEntryAll().observe(this, entries ->{
                adapter.submitList(entries, () -> {
                    if (entries != null && !entries.isEmpty()) {
                        recyclerView.scrollToPosition(0);
                    }
                });
            });

            //setup RecyclerView
            this. adapter = new JournalEntryAdapter(new OnJournalFeedbackListener() {
                @Override
                public void onCorrectClick(JournalEntry entry) {
                    viewModel.markCorrect(entry.getJournal_id());
                }

                @Override
                public void onWrongClick(JournalEntry entry) {
                    showCorrectionSheet(entry);
                }
            });
            this.recyclerView = findViewById(R.id.recyclerView);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(adapter);

            // Save button (FloatingActionButton)
            findViewById(R.id.fabSave).setOnClickListener(v -> onSaveClicked());
        } catch (Exception e) {
            Log.e("AI_ERROR", "Failed to load AI model: " + e.getMessage());
            // Show a Toast so you know it failed without crashing
            Toast.makeText(this, "AI Analysis currently unavailable", Toast.LENGTH_LONG).show();
        }

    }


    public void onSaveClicked() {
        TextInputLayout layout = findViewById(R.id.textInputLayout);
        TextInputEditText et = findViewById(R.id.etJournalText);

        String text = et.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            layout.setError("Journal text is required");
            return;
        }

        layout.setErrorEnabled(false);
        et.setText("");

        viewModel.saveEntry(text);
    }
    private void showCorrectionSheet(JournalEntry entry) {
        String[] emotions = {"sadness", "joy", "love", "anger", "fear", "surprise"};

        new MaterialAlertDialogBuilder(this)
                .setTitle("What was the correct emotion?")
                .setItems(emotions, (dialog, which) -> {
                    // Save correction silently in background
                    entry.setCorrect(false);
                    entry.correctedEmotion = emotions[which];
                    viewModel.markWrong(entry.getJournal_id());
                    viewModel.updateCorrectedEmotion(entry.getJournal_id(), entry.getCorrectedEmotion());
                    Toast.makeText(this, "the corrected emotion is "+ emotions[which], Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Skip", (dialog, which) -> {
                    // Still mark as wrong even if they skip
                    entry.setCorrect(false);
                    viewModel.markWrong(entry.getJournal_id());
                })
                .show();
    }
}