package com.nikoarap.favqsapp.ui.search;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.utils.PopulateRecyclerView;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends AndroidViewModel {

    private String word = "";
    private MutableLiveData<String> queryWord = new MutableLiveData<>();
    private static Quotes[] quotes;
    private PopulateRecyclerView populateRecyclerView;
    private static ArrayList<Quotes> quoteList = new ArrayList<>();

    private static final String TAG = "AAAAAAAAAAA";



    public SearchViewModel(@NonNull Application application) {
        super(application);
    }


    //gets quotes by user input in the Search Query
    void initSearchView(SearchView searchView, RecyclerView recyclerView, QuotesAdapter.OnQuoteListener quoteListener){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                word = query;
                queryWord.postValue(word);
                //fetches the quotes that match the query from the server
                fetchQuoteList(word, recyclerView, quoteListener);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    void fetchQuoteList(String query, RecyclerView recView, QuotesAdapter.OnQuoteListener quoteListener) {
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
                        Toast.makeText(getApplication(), R.string.no_quotes_found, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: "+ response.body());
                    }
                    else{
                        populateRecyclerView = new PopulateRecyclerView(Objects.requireNonNull(getApplication()),recView);
                        populateRecyclerView.populate(quotes,quoteListener);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(getApplication(), R.string.error ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<String> getText() {
        return queryWord;
    }

    ArrayList<Quotes> getQuoteList(){
        return quoteList;
    }

}
