package com.example.heathwellnessassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.ViewModel.JournalViewModel;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText inputField;
    TextView outputFiled;
    Button addBtn;
    JournalViewModel journalViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.etText_context);
        outputFiled = findViewById(R.id.tv01);
        addBtn = findViewById(R.id.button);

        journalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);

        addBtn.setOnClickListener(v -> {
            String entryText = inputField.getText().toString();
            if(!entryText.isEmpty()){
                JournalEntry newEntry = new JournalEntry();
                newEntry.setText_context(entryText);
                newEntry.setDate(new Date());
                journalViewModel.insetJournalEntry(newEntry);
                inputField.setText("");
            }
        });

        journalViewModel.getJournalEntryAll().observe(this, entries ->{
            StringBuilder allEntries = new StringBuilder();
            for(JournalEntry entry: entries){
                allEntries.append(entry.getText_context()).append("");
            }
            outputFiled.setText(allEntries.toString());
        });
    }
}