package com.nikoarap.favqsapp.repository;

import android.util.Log;

import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {

    private static final String TAG = "OOOOOOOOOOOOOOOOOO";
    private final static int NETWORK_TIMEOUT = 3000;

    private static DataRepository instance;
    private RetrieveQuotesRunnable retrieveQuotesRunnable;

    private MutableLiveData<QuoteModel> quotesList;

    public static DataRepository getInstance(){
        if(instance == null){
            instance = new DataRepository();
        }
        return instance;
    }

    private DataRepository(){
        quotesList = new MutableLiveData<>();
    }

    public LiveData<QuoteModel> getQuotesList(){
        return quotesList;
    }

    //server request for quotes
    public void quotesApiRequest(){
        if(retrieveQuotesRunnable != null){
            retrieveQuotesRunnable = null;
        }
        retrieveQuotesRunnable = new RetrieveQuotesRunnable();
        final Future handler = AppExecutors.getInstance().getExec().submit(retrieveQuotesRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(() -> {
            handler.cancel(true);
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    // background thread that will handle the request to the server
    private class RetrieveQuotesRunnable implements Runnable {

        public RetrieveQuotesRunnable(){
        }

        @Override
        public void run() { // actual request happens inside the background thread
            try {
                Response response = getQuotes().execute();
                if (response.code() == 200 && response.body() != null){
                    QuoteModel quoteModel =((QuoteModel)response.body());
                    quotesList.postValue(quoteModel);
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error );
                    quotesList.postValue(null);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: " + e);
                quotesList.postValue(null);
            }
        }

        //the call to fetch the json data from the api
        private Call<QuoteModel> getQuotes(){
            return RetrofitRequestClass.fetchApi().getQuotesApi();
        }
    }


}
