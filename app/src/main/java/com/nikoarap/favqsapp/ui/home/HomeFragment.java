package com.nikoarap.favqsapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

public class HomeFragment extends Fragment implements QuotesAdapter.OnQuoteListener{

    private Quotes[] quotes;
    private QuoteModel quoteModel;
    private PopulateRecyclerView populateRecyclerView;
    private RecyclerView recView;
    private ArrayList<Quotes> quoteList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        recView = root.findViewById(R.id.quotesRecyclerView);

        //gets the saved user from SharedPreferences
        String loginResponse = prefs.getString("login", "defaultStringIfNothingFound");

        //set title to Action Bar
        Objects.requireNonNull(((AppCompatActivity)
                Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Welcome, "+ loginResponse);

        fetchQuoteList(this);

        return root;
    }

    //fetches a random list of quotes from the server
    private void fetchQuoteList(QuotesAdapter.OnQuoteListener quoteListener) {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesApi();
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(@NotNull Call<QuoteModel> call, @NotNull Response<QuoteModel> response) {
                if (response.body() != null) {
                    quoteModel = response.body();
                    quotes = quoteModel.getQuotes();
                    quoteList.addAll(Arrays.asList(quotes));
                    populateRecyclerView = new PopulateRecyclerView(Objects.requireNonNull(getActivity()),recView);
                    populateRecyclerView.populate(quotes,quoteListener);
                }
            }

            @Override
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onQuoteClick(int position) {
        Intent i = new Intent(getActivity(), QuoteActivity.class);
        i.putExtra("quote", quoteList.get(position));
        startActivity(i);
    }
}