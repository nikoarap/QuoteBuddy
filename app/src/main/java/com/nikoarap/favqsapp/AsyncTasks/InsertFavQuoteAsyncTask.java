package com.nikoarap.favqsapp.AsyncTasks;

import android.os.AsyncTask;

import com.nikoarap.favqsapp.db.AppDao;
import com.nikoarap.favqsapp.models.Quotes;

public class InsertFavQuoteAsyncTask extends AsyncTask<Quotes, Void, Void> {

    private AppDao appDao;

    public InsertFavQuoteAsyncTask(AppDao dao) {
        appDao = dao;
    }

    @Override
    protected Void doInBackground(Quotes... favQuote) {
        appDao.insertFavQuote(favQuote);
        return null;
    }
}