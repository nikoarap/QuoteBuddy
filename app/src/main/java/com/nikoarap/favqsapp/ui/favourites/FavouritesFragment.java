package com.nikoarap.favqsapp.ui.favourites;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikoarap.favqsapp.AsyncTasks.DeleteFavQuoteAsyncTask;
import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.adapters.FavQuotesAdapter;
import com.nikoarap.favqsapp.adapters.QuotesAdapter;
import com.nikoarap.favqsapp.db.AppDao;
import com.nikoarap.favqsapp.db.AppDatabase;
import com.nikoarap.favqsapp.models.Quotes;
import com.nikoarap.favqsapp.ui.QuoteActivity;
import com.nikoarap.favqsapp.utils.VerticalSpacingDecorator;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements FavQuotesAdapter.OnQuoteListener {

    public static final String TAG = "FavouritesFragment";

    private Context context;
    private AppDao appDao;
    private AppDatabase appDatabase;

    private RecyclerView recView;
    public ArrayList<Quotes> quotesList = new ArrayList<>();

    private FavouritesViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(FavouritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        recView = root.findViewById(R.id.quotesRecyclerView);
        recView.setNestedScrollingEnabled(false);

        context = FavouritesFragment.this.getActivity();
        appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        appDao = appDatabase.getAppDao();

        observeFromDb();

        return root;
    }

    //observing data changed from the DB
    private void observeFromDb(){
        appDao.getFavdQuotes().observe(this, quotes -> {
            if (quotes != null){
                for(Quotes quote: quotes){
                    Log.d(TAG, "onChanged: " + quote);
                    populateRecyclerView(quotes);
                    quotesList.add(quote);
                }
            }
        });
    }

    private void populateRecyclerView(List<Quotes> quoteList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recView.setLayoutManager(linearLayoutManager);
        FavQuotesAdapter recAdapter = new FavQuotesAdapter(getActivity(), quoteList, this);
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(1);
        recView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recView);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    //delete quote by swiping to the right
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteFavQuoteTask(quotesList.get(viewHolder.getAdapterPosition()));
        }
    };

    private void deleteFavQuoteTask(Quotes quote) {
        new DeleteFavQuoteAsyncTask(appDao).execute(quote);
    }

    @Override
    public void onQuoteClick(int position) {

    }
}