package com.example.heathwellnessassistant.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heathwellnessassistant.Entities.JournalEntry;
import com.example.heathwellnessassistant.R;

public class JournalEntryAdapter extends ListAdapter<JournalEntry, JournalEntryAdapter.ViewHolder> {

    public JournalEntryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<JournalEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<JournalEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {
                    return oldItem.getJournal_id() == newItem.getJournal_id();
                }

                @Override
                public boolean areContentsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {
                    return oldItem.getText_context().equals(newItem.getText_context())
                            && oldItem.getAi_sentiment().equals(newItem.getAi_sentiment())
                            && Math.abs(oldItem.getAi_confidence() - newItem.getAi_confidence()) < 1e-4f
                            && oldItem.getDate().equals(newItem.getDate());
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = getItem(position);
        holder.bind(entry);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvText;
        private final TextView tvSentiment;
        private final TextView tvConfidence;
        private final TextView tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            tvSentiment = itemView.findViewById(R.id.tvSentiment);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvConfidence = itemView.findViewById(R.id.tvConfidence);
        }

        void bind(JournalEntry entry) {
            tvText.setText(entry.getText_context());
            tvSentiment.setText(entry.getAi_sentiment());

            // UX polish: color sentiment
            int color = Color.BLACK;
            String sentiment = entry.getAi_sentiment();
            if (sentiment != null) {
                if (sentiment.contains("sadness") || sentiment.contains("anger") || sentiment.contains("fear")) {
                    color = Color.RED;
                } else if (sentiment.contains("joy") || sentiment.contains("love")) {
                    color = Color.GREEN;
                }
            }
            tvText.setTextColor(color);

            tvConfidence.setText(
                    String.format("%.1f%%", entry.getAi_confidence() * 100)
            );

            tvDate.setText(
                    android.text.format.DateFormat.format("MMM dd, yyyy", entry.getDate())
            );
        }
    }
}