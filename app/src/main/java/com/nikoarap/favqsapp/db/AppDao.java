package com.nikoarap.favqsapp.db;

import com.nikoarap.favqsapp.models.Quotes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavQuote(Quotes... favQuote);

    @Delete
    void delete(Quotes... quote);

    @Query("SELECT * FROM quotes")
    LiveData<List<Quotes>> getFavdQuotes();


}