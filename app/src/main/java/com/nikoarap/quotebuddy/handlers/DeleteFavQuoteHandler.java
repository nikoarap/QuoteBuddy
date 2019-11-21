package com.nikoarap.quotebuddy.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.db.AppDao;
import com.nikoarap.quotebuddy.models.Quotes;


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
