package com.nikoarap.favqsapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private Quotes[] quotes;

    @BindView(R.id.quoteText) TextView quoteText;
    @BindView(R.id.authorText) TextView authorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        //gets the saved user from SharedPreferences
        String tokenResponse = prefs.getString(getString(R.string.tokenResponse), "defaultStringIfNothingFound");

        fetchRandomQuote();

        handler.postDelayed(() -> {
            //checks if the user-token that is saved in SharedPreferences has the value of the login session we requested from the server
            //if it's not valid that means that the user is not logged in and the app navigates to LoginActivity
            if(tokenResponse.equals("defaultStringIfNothingFound")){
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
            //if the user-token has the proper value that means that the user is logged in and the app navigates to MainActivity
            else{
                Intent i = new Intent(SplashActivity.this, UserMenuActivity.class);
                startActivity(i);
            }
            SplashActivity.this.finish();
        }, 3500);

    }

    //fetches a random quote from the server
    private void fetchRandomQuote() {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesApi();
        call.enqueue(new Callback<QuoteModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<QuoteModel> call, @NotNull Response<QuoteModel> response) {
                if (response.body() != null) {
                    QuoteModel quoteModel = response.body();
                    quotes = quoteModel.getQuotes();
                    String body = quotes[0].getBody();
                    String author = quotes[0].getAuthor();
                    quoteText.setText("“"+body+"”");
                    authorText.setText("-"+author);
                }
            }

            @Override
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(SplashActivity.this, "error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //stops handler and destroys the Activity
        handler.removeCallbacksAndMessages(null);
        finish();
    }
}
