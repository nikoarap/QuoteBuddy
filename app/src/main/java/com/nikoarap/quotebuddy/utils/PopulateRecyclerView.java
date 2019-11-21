package com.nikoarap.quotebuddy.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nikoarap.quotebuddy.adapters.QuotesAdapter;
import com.nikoarap.quotebuddy.models.Quotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ViewConstructor")
public class PopulateRecyclerView extends RecyclerView {

    private RecyclerView recView;

    public PopulateRecyclerView(@NonNull Context context, RecyclerView recView) {
        super(context);
        this.recView = recView;
    }

    public void populate(Quotes[] quoteList, QuotesAdapter.OnQuoteListener quoteListener) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(linearLayoutManager);
        QuotesAdapter recAdapter = new QuotesAdapter(quoteList, quoteListener);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }
}
