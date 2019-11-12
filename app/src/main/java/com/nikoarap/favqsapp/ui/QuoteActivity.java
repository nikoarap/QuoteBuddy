package com.nikoarap.favqsapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nikoarap.favqsapp.AsyncTasks.DeleteFavQuoteAsyncTask;
import com.nikoarap.favqsapp.AsyncTasks.InsertFavQuoteAsyncTask;
import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.db.AppDao;
import com.nikoarap.favqsapp.db.AppDatabase;
import com.nikoarap.favqsapp.models.Quotes;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuoteActivity extends AppCompatActivity {

    private AppDao appDao;

    //binds views in Butterknife
    @BindView(R.id.quote_body) TextView bodyTv;
    @BindView(R.id.quote_author) TextView authorTv;
    @BindView(R.id.quote_upvotes) TextView upvotesTv;
    @BindView(R.id.quote_downvotes) TextView downvotesTv;
    @BindView(R.id.quote_favcount) TextView favcountTv;
    @BindView(R.id.quote_tags) TextView tagsTv;
    @BindView(R.id.button_favorite) ToggleButton btnFav;

    private String quoteAuthor;
    private String quoteAuthorPerma;
    private String[] quoteTags;
    private Quotes quote;

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
        String quoteId = i.getStringExtra("quoteId");
        String quoteBody = i.getStringExtra("quoteBody");
        quoteAuthor = i.getStringExtra("quoteAuthor");
        quoteAuthorPerma = i.getStringExtra("quoteAuthorPerma");
        String quoteUpvotes = i.getStringExtra("quoteUpvotes");
        String quoteDownvotes = i.getStringExtra("quoteDownvotes");
        quoteTags = i.getStringArrayExtra("guoteTags");
        String quoteFavCount = i.getStringExtra("quoteFavCount");

        extractTags();

        bodyTv.setText("“"+ quoteBody +"”");
        authorTv.setText("-"+quoteAuthor);
        upvotesTv.setText(quoteUpvotes);
        downvotesTv.setText(quoteDownvotes);
        favcountTv.setText(quoteFavCount);

        //quote model
        quote = new Quotes(null, quoteFavCount, quoteAuthor, null, quoteUpvotes,
                quoteAuthorPerma, quoteId, quoteBody, null, quoteTags, quoteDownvotes);


        //favoriteButton implementation--1st click favourites the quote, 2nd click unfavourites it
        //by favouriting a quote, it is stored in SQLite, by Unfavouriting it it is deleted
        btnFav.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                insertFavQuoteTask(quote);
                Toast.makeText(QuoteActivity.this, "Quote added to Favorites list!", Toast.LENGTH_SHORT).show();
            }else{
                deleteFavQuoteTask(quote);
                Toast.makeText(QuoteActivity.this, "Quote deleted from Favorites list", Toast.LENGTH_SHORT).show();
            }

        });

        //clicks on the Author Name Text View pass to an activity that displays quotes from this Author
        authorTv.setOnClickListener(v -> {
            Intent intent = new Intent(QuoteActivity.this,AuthorActivity.class);
            intent.putExtra("quoteAuthor", quoteAuthor);
            intent.putExtra("quoteAuthorPerma", quoteAuthorPerma);
            intent.putExtra("quoteId", quoteAuthorPerma);
            intent.putExtra("quoteBody", quoteAuthorPerma);
            intent.putExtra("quoteUpvotes", quoteAuthorPerma);
            intent.putExtra("quoteDownvotes", quoteAuthorPerma);
            intent.putExtra("guoteTags", quoteAuthorPerma);
            intent.putExtra("quoteFavCount", quoteAuthorPerma);
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

    private void insertFavQuoteTask(Quotes quote) {
        new InsertFavQuoteAsyncTask(appDao).execute(quote);
    }

    private void deleteFavQuoteTask(Quotes quote) {
        new DeleteFavQuoteAsyncTask(appDao).execute(quote);
    }








}
