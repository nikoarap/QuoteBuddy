package com.nikoarap.quotebuddy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nikoarap.quotebuddy.handlers.DeleteFavQuoteHandler;
import com.nikoarap.quotebuddy.handlers.InsertFavQuoteHandler;
import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.db.AppDao;
import com.nikoarap.quotebuddy.db.AppDatabase;
import com.nikoarap.quotebuddy.models.Quotes;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuoteActivity extends AppCompatActivity {

    private AppDao appDao;
    private String quoteAuthor;
    private String[] quoteTags;
    private Quotes quote;

    //binds views in Butterknife
    @BindView(R.id.quote_body) TextView bodyTv;
    @BindView(R.id.quote_author) TextView authorTv;
    @BindView(R.id.quote_upvotes) TextView upvotesTv;
    @BindView(R.id.quote_downvotes) TextView downvotesTv;
    @BindView(R.id.quote_favcount) TextView favcountTv;
    @BindView(R.id.quote_tags) TextView tagsTv;
    @BindView(R.id.button_favorite) ToggleButton btnFav;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_activity_layout);
        ButterKnife.bind(this);

        //initialize DB in activity
        Context context = QuoteActivity.this.getApplicationContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        appDao = appDatabase.getAppDao();

        //gets intent from previous activity along with the passed values
        Intent i = getIntent();
        quote = Objects.requireNonNull(i.getExtras()).getParcelable(getString(R.string.quote));

        String quoteBody = Objects.requireNonNull(quote).getBody();
        quoteAuthor = quote.getAuthor();
        String quoteUpvotes = quote.getUpvotes_count();
        String quoteDownvotes = quote.getDownvotes_count();
        quoteTags = quote.getTags();
        String quoteFavCount = quote.getFavorites_count();

        extractTags();

        bodyTv.setText("“"+ quoteBody +"”");
        authorTv.setText("-"+quoteAuthor);
        upvotesTv.setText(quoteUpvotes);
        downvotesTv.setText(quoteDownvotes);
        favcountTv.setText(quoteFavCount);


        //favoriteButton implementation--1st click favourites the quote, 2nd click unfavourites it
        //by favouriting a quote, it is stored in SQLite, by Unfavouriting it it is deleted
        btnFav.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                insertFavQuote(quote);
            }else{
                deleteFavQuoteTask(quote);
            }
        });

        //clicks on the Author Name Text View pass to an activity that displays quotes from this Author
        authorTv.setOnClickListener(v -> {
            Intent intent = new Intent(QuoteActivity.this,AuthorActivity.class);
            intent.putExtra(getString(R.string.quoteAuthor), quoteAuthor);
            startActivity(intent);
        });

    }

    //extracts the Tags and concatenates them to a text view
    public void extractTags(){
        int i;
        for(i = 0; i < quoteTags.length; i++ ){
            tagsTv.append("  "+quoteTags[i]);
        }
    }

    private void insertFavQuote(Quotes favQuote) {
        new InsertFavQuoteHandler(appDao,this).executeHandler(favQuote);

    }

    private void deleteFavQuoteTask(Quotes favQuote) {
        new DeleteFavQuoteHandler(appDao, this).executeHandler(favQuote);
    }








}
