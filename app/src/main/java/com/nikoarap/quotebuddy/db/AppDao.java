package com.nikoarap.quotebuddy.db;

import com.nikoarap.quotebuddy.models.Quotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AppDao {

    //store favourited quotes into the db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavQuote(Quotes... favQuote);

    //delete favourited quotes from the db
    @Delete
    void deleteFavQuote(Quotes... quote);

    //select all favourited quotes from the db
    @Query("SELECT * FROM quotes")
    LiveData<Quotes[]> getFavdQuotes();


}