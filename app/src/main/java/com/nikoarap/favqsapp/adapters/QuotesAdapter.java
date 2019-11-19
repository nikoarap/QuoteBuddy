package com.nikoarap.favqsapp.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.models.Quotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//RecyclerfView Adapter for quoteList
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private Quotes[] quotesList;
    private OnQuoteListener onQuoteListener;
    private static final String TAG = "AuthorsAdapter";

    public QuotesAdapter(Quotes[] quotesList, OnQuoteListener quoteListener) {
        this.quotesList = quotesList;
        this.onQuoteListener = quoteListener;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_list_item_layout, viewGroup, false);
        return new QuotesViewHolder(view,onQuoteListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder viewHolder, int position) {
        viewHolder.quoteText.setText("“"+quotesList[position].getBody()+"”");
        viewHolder.authorText.setText("-"+quotesList[position].getAuthor());
    }

    @Override
    public int getItemCount() {
        if (null == quotesList) return 0;
        return quotesList.length;
    }

    //defines the UI and functionality of the recyclerView and its' listItems
    public class QuotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnQuoteListener onQuoteListener;
        private TextView quoteText;
        private TextView authorText;

        private QuotesViewHolder(View v, OnQuoteListener quoteListener) {
            super(v);
            quoteText = v.findViewById(R.id.quote_body);
            authorText = v.findViewById(R.id.quote_author);
            onQuoteListener = quoteListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item number: " + getAdapterPosition());
            onQuoteListener.onQuoteClick(getAdapterPosition()); //click listener gets the position of each individual listItem
        }
    }

    public interface OnQuoteListener {
        void onQuoteClick(int position);
    }
}
