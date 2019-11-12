package com.nikoarap.favqsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.preferences.PreferenceHelper;
import com.nikoarap.favqsapp.utils.VerticalSpacingDecorator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements QuotesAdapter.OnQuoteListener {

    private static String tokenResponse;
    private static String loginResponse;
    private static String emailResponse;

    private Quotes[] quotes;

    private RecyclerView recView;
    public ArrayList<Quotes> quoteList = new ArrayList<>();

//    @BindView(R.id.utext1) TextView tokenText;
//    @BindView(R.id.utext2)  TextView loginText;
//    @BindView(R.id.utext3)  TextView emailText;
      @BindView(R.id.name)  TextView usernameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        recView = findViewById(R.id.quotesRecyclerView);
        ButterKnife.bind(this);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(UserActivity.this);
        tokenResponse = prefs.getString("token", "defaultStringIfNothingFound");
        loginResponse = prefs.getString("login", "defaultStringIfNothingFound");
        emailResponse = prefs.getString("email", "defaultStringIfNothingFound");

        usernameTv.setText("Welcome "+ loginResponse);

        fetchQuoteList();

    }

    private void fetchQuoteList() {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesApi();
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (response.body() != null) {
                    QuoteModel quoteModel = response.body();
                    quoteModel.setQuotes(response.body().getQuotes());
                    quotes = quoteModel.getQuotes();
                    for(Quotes quote: quotes){
                        populateRecyclerView(quotes);
                        quoteList.add(quote);

                    }

                }
            }

            @Override
            public void onFailure(Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(UserActivity.this, "error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateRecyclerView(Quotes[] quoteList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        QuotesAdapter recAdapter = new QuotesAdapter(this, quoteList, this);
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(1);
        recView.addItemDecoration(itemDecorator);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onQuoteClick(int position) {

    }



}
