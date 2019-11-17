package com.nikoarap.favqsapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.ui.QuoteActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment implements QuotesAdapter.OnQuoteListener {

    private RecyclerView recView;
    private SearchViewModel searchViewModel;

    // restores the state after screen rotation
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //observes and gets the string (search query) from fragment's previous saved state
            ObserveQuery();
        }else{
            Toast.makeText(getActivity(), "state null", Toast.LENGTH_SHORT).show();

        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView searchView = root.findViewById(R.id.searchView);
        recView = root.findViewById(R.id.quotesRecyclerView_search);
        //initializes searchView from ViewModel
        searchViewModel.initSearchView(searchView,recView,this);
        return root;
    }


    private void ObserveQuery(){
        searchViewModel.getText().observe(this, query ->
                searchViewModel.fetchQuoteList(query, recView, SearchFragment.this));
    }

    @Override
    public void onQuoteClick(int position) {
        Intent i = new Intent(getActivity(), QuoteActivity.class);
        ArrayList<Quotes> quoteList = searchViewModel.getQuoteList();
        i.putExtra(getString(R.string.quote), quoteList.get(position));
        startActivity(i);
    }

//    //saves the state of the fragment
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //here, the String of the search query is stored in Bundle to be used later
//        outState.putString(getString(R.string.restoreKey), word);
//    }

}