package com.nikoarap.favqsapp.AsyncTasks;

import android.os.AsyncTask;

import com.nikoarap.favqsapp.db.AppDao;
import com.nikoarap.favqsapp.models.Quotes;

public class DeleteFavQuoteAsyncTask  extends AsyncTask<Quotes, Void, Void> {

    private AppDao appDao;

    public DeleteFavQuoteAsyncTask(AppDao dao) {
        appDao = dao;
    }

    @Override
    protected Void doInBackground(Quotes... quotes) {
        appDao.delete(quotes);
        return null;
    }
}
