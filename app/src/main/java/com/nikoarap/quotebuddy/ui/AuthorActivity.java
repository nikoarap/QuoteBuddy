package com.nikoarap.quotebuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.utils.PopulateRecyclerView;
import com.nikoarap.quotebuddy.adapters.QuotesAdapter;
import com.nikoarap.quotebuddy.api.APIHandlingService;
import com.nikoarap.quotebuddy.api.RetrofitRequestClass;
import com.nikoarap.quotebuddy.models.QuoteModel;
import com.nikoarap.quotebuddy.models.Quotes;
import com.nikoarap.quotebuddy.utils.VerticalSpacingDecorator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorActivity extends AppCompatActivity implements QuotesAdapter.OnQuoteListener{

    private static Quotes[] quotes;
    private String quoteAuthor_2;
    private RecyclerView recView;
    private PopulateRecyclerView populateRecyclerView;
    public ArrayList<Quotes> quoteList = new ArrayList<>();
    private VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        recView = findViewById(R.id.quotesRecyclerView);
        recView.addItemDecoration(itemDecorator);
        ButterKnife.bind(this);

        //gets intent from previous activity along with the passed values
        Intent i = getIntent();
        String quoteAuthor = i.getStringExtra(getString(R.string.quoteAuthor));

        //sets title to actionBar
        Objects.requireNonNull(AuthorActivity.this.getSupportActionBar())
                .setTitle(quoteAuthor);

        //converts string to be properly added to the server request
        quoteAuthor_2 = Objects.requireNonNull(quoteAuthor).replaceAll("\\s+","+");

        //fetches the list of quotes by the corresponding Author
        fetchQuoteList(this);
    }

    private void fetchQuoteList(QuotesAdapter.OnQuoteListener quoteListener) {
        APIHandlingService service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesByAuthor(quoteAuthor_2,"&",getString(R.string.author));
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(@NotNull Call<QuoteModel> call, @NotNull Response<QuoteModel> response) {
                if (response.body() != null) {
                    QuoteModel quoteModel = response.body();
                    quotes = quoteModel.getQuotes();
                    quoteList.addAll(Arrays.asList(quotes));
                    populateRecyclerView = new PopulateRecyclerView(getApplicationContext(),recView);
                    populateRecyclerView.populate(quotes,quoteListener);
                }
            }

            @Override
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(AuthorActivity.this, R.string.error ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //listens to clicks given in each position in the RecyclerView, then goes to the next Activity passing the corresponding data
    @Override
    public void onQuoteClick(int position) {
        Intent i = new Intent(AuthorActivity.this, QuoteActivity.class);
        i.putExtra(getString(R.string.quote), quoteList.get(position));
        startActivity(i);
    }

    //goes to the main activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AuthorActivity.this,UserMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
