package com.nikoarap.favqsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.utils.VerticalSpacingDecorator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorActivity extends AppCompatActivity implements QuotesAdapter.OnQuoteListener{

    public static final String TAG = "AuthorActivity";

    private Quotes[] quotes;
    private String quoteAuthor_2;

    private RecyclerView recView;
    public ArrayList<Quotes> quoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        recView = findViewById(R.id.quotesRecyclerView);
        ButterKnife.bind(this);

        //gets intent from previous activity along with the passed values
        Intent i = getIntent();
        String quoteAuthor = i.getStringExtra("quoteAuthor");

        //sets title to actionBar
        Objects.requireNonNull(AuthorActivity.this.getSupportActionBar())
                .setTitle(quoteAuthor);

        //converts string to be properly added to the server request
        quoteAuthor_2 = quoteAuthor.replaceAll("\\s+","+");

        fetchQuoteList();
    }

    //fetches the list of quotes by the corresponding Author
    private void fetchQuoteList() {
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        Call<QuoteModel> call = service.getQuotesByAuthor(quoteAuthor_2,"&","author");
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(@NotNull Call<QuoteModel> call, @NotNull Response<QuoteModel> response) {
                if (response.body() != null) {
                    String str = Arrays.toString(response.body().getQuotes());
                    Log.i(TAG, "onResponse: "+str);
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
            public void onFailure(@NotNull Call<QuoteModel> call, @NotNull Throwable t) {
                Toast.makeText(AuthorActivity.this, "error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //fills the Recycler view with listItems - quotes
    private void populateRecyclerView(Quotes[] quoteList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        QuotesAdapter recAdapter = new QuotesAdapter(quoteList, this);
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(1);
        recView.addItemDecoration(itemDecorator);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    //listens to clicks given in each position in the RecyclerView, then goes to the next Activity passing the corresponding data
    @Override
    public void onQuoteClick(int position) {
        Intent i = new Intent(AuthorActivity.this, QuoteActivity.class);
        i.putExtra("quoteId", quoteList.get(position).getId());
        i.putExtra("quoteBody", quoteList.get(position).getBody());
        i.putExtra("quoteAuthor", quoteList.get(position).getAuthor());
        i.putExtra("quoteAuthorPerma", quoteList.get(position).getAuthor_permalink());
        i.putExtra("quoteUpvotes", quoteList.get(position).getUpvotes_count());
        i.putExtra("quoteDownvotes", quoteList.get(position).getDownvotes_count());
        i.putExtra("guoteTags", quoteList.get(position).getTags());
        i.putExtra("quoteFavCount", quoteList.get(position).getFavorites_count());
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
