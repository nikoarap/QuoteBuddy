package com.nikoarap.favqsapp.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.adapters.PopulateRecyclerView;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.ui.QuoteActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//resets after tapping elsewhere
public class SearchFragment extends Fragment implements QuotesAdapter.OnQuoteListener {

    private static Quotes[] quotes;
    private RecyclerView recView;
    private PopulateRecyclerView populateRecyclerView;
    private ArrayList<Quotes> quoteList = new ArrayList<>();
    private SearchView searchView;
    private static String word = "";
    private QuotesAdapter.OnQuoteListener quoteListener;


    // restores the state after screen rotation
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            savedInstanceState.getString(getString(R.string.restoreKey));
            fetchQuoteList(word,quoteListener);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = root.findViewById(R.id.searchView);
        recView = root.findViewById(R.id.quotesRecyclerView);
        initSearchView(this);
        return root;
    }

    //gets quotes by user input in the Search Query
    private void initSearchView(QuotesAdapter.OnQuoteListener quoteListener){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                word = query;
                //fetches the quotes that match the query from the server
                fetchQuoteList(word,quoteListener);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    private void fetchQuoteList(String query, QuotesAdapter.OnQuoteListener quoteListener) {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesByWord(query);
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(@NotNull Call<QuoteModel> call, @NotNull Response<QuoteModel> response) {
                if (response.body() != null) {
                    QuoteModel quoteModel = response.body();
                    quotes = quoteModel.getQuotes();
                    quoteList.clear();
                    quoteList.addAll(Arrays.asList(quotes));
                    if (quoteList.get(0).getAuthor() == null) {
                        recView.removeAllViewsInLayout();
                        Toast.makeText(getActivity(), R.string.no_quotes_found, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        populateRecyclerView = new PopulateRecyclerView(Objects.requireNonNull(getActivity()),recView);
                        populateRecyclerView.populate(quotes,quoteListener);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.error ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onQuoteClick(int position) {
        Intent i = new Intent(getActivity(), QuoteActivity.class);
        i.putExtra(getString(R.string.quote), quoteList.get(position));
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.restoreKey), word);

    }
}