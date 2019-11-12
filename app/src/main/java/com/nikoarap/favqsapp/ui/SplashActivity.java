package com.nikoarap.favqsapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.viewmodels.QuoteViewmodel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private Quotes[] quotes;

    @BindView(R.id.quoteText) TextView quoteText;
    @BindView(R.id.authorText) TextView authorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);

        fetchRandomQuote();

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(3500);
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        logoTimer.start();
    }

    private void fetchRandomQuote() {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesApi();
        call.enqueue(new Callback<QuoteModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (response.body() != null) {
                    QuoteModel quoteModel = response.body();
                    quoteModel.setQuotes(response.body().getQuotes());
                    quotes = quoteModel.getQuotes();
                    String body = quotes[0].getBody();
                    String author = quotes[0].getAuthor();
                    quoteText.setText("“"+body+"”");
                    authorText.setText("-"+author);
                }
            }

            @Override
            public void onFailure(Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(SplashActivity.this, "error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
