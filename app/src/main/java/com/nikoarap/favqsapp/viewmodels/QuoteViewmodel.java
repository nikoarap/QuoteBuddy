package com.nikoarap.favqsapp.viewmodels;

import android.app.Application;

import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.repository.DataRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class QuoteViewmodel extends AndroidViewModel {

    private DataRepository dataRepository;

    public QuoteViewmodel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance();
    }

    //caches from the server to the db and then to the corresponding activity
    public LiveData<QuoteModel> getQuotesList(){
        return dataRepository.getQuotesList();
    }

    // sends request to the server and retrofit from the corresponding activity
    public void quotesApiRequest(){
        dataRepository.quotesApiRequest();
    }
}
