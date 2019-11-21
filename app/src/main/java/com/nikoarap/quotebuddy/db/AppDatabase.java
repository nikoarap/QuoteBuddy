package com.nikoarap.quotebuddy.db;

import android.content.Context;

import com.nikoarap.quotebuddy.models.Quotes;
import com.nikoarap.quotebuddy.utils.Converters;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Quotes.class}, version = 2) // schema updated in build.gradle(App)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Favorites.db";

    private static AppDatabase instance;

    //db constructor and instantiation
    public static AppDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME
                    ).build();
        }
        return instance;
    }

    public abstract AppDao getAppDao();


}
