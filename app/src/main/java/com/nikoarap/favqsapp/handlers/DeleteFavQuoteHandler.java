package com.nikoarap.favqsapp.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.db.AppDao;
import com.nikoarap.favqsapp.models.Quotes;


public class DeleteFavQuoteHandler {

    private Handler mHandler;
    private AppDao appDao;
    private Context context;

    public DeleteFavQuoteHandler(AppDao dao, Context context) {
        this.context = context;
        appDao = dao;
    }

    private void startHandlerThread(){
        HandlerThread mHandlerThread = new HandlerThread("DeleteHandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void executeHandler(Quotes... favQuote){
        startHandlerThread();
        mHandler.post(() ->
                appDao.deleteFavQuote(favQuote));
        Toast.makeText(context, R.string.quote_deleted, Toast.LENGTH_SHORT).show();
    }
}
