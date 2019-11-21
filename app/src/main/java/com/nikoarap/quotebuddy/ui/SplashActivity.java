package com.nikoarap.quotebuddy.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.api.APIHandlingService;
import com.nikoarap.quotebuddy.api.RetrofitRequestClass;
import com.nikoarap.quotebuddy.models.QuoteModel;
import com.nikoarap.quotebuddy.models.Quotes;
import com.nikoarap.quotebuddy.preferences.PrefsHelper;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private PrefsHelper prefsHelper = new PrefsHelper();
    private Quotes[] quotes;

    @BindView(R.id.quoteText) TextView quoteText;
    @BindView(R.id.authorText) TextView authorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);

        //gets the saved user token from SharedPreferences
        String tokenResponse = prefsHelper.getStringfromPrefs(getString(R.string.tokenResponse), SplashActivity.this);

        fetchRandomQuote();

        handler.postDelayed(() -> {
            //checks if the user-token that is saved in SharedPreferences has the value of the login session we requested from the server
            //if it's not valid that means that the user is not logged in and the app navigates to LoginActivity
            if(tokenResponse.equals(getString(R.string.defaultStringIfNothingFound))){
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
        APIHandlingService service = RetrofitRequestClass.fetchApi();
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
                Toast.makeText(SplashActivity.this, R.string.error ,Toast.LENGTH_SHORT).show();
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
