package com.nikoarap.favqsapp.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.models.Quotes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavQuotesAdapter extends RecyclerView.Adapter<FavQuotesAdapter.FavQuotesViewHolder> {

    private List<Quotes> quotesList;
    private OnQuoteListener onQuoteListener;
    private static final String TAG = "FavQuotesAdapter";

    public FavQuotesAdapter(List<Quotes> quotesList, OnQuoteListener quoteListener) {
        this.quotesList = quotesList;
        this.onQuoteListener = quoteListener;
    }

    @NonNull
    @Override
    public FavQuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_list_item_layout, viewGroup, false);
        return new FavQuotesViewHolder(view,onQuoteListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavQuotesViewHolder viewHolder, int position) {
        viewHolder.quoteText.setText("“"+quotesList.get(position).getBody()+"”");
        viewHolder.authorText.setText("-"+quotesList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        if (null == quotesList) return 0;
        return quotesList.size();
    }

    //defines the UI and functionality of the recyclerView and its' listItems
    public class FavQuotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnQuoteListener onQuoteListener;
        private TextView quoteText;
        private TextView authorText;

        private FavQuotesViewHolder(View v, OnQuoteListener quoteListener) {
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

    public interface OnQuoteListener{
        void onQuoteClick(int position);
    }

}
