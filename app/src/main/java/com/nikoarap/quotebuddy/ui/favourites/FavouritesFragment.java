package com.nikoarap.quotebuddy.ui.favourites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.handlers.DeleteFavQuoteHandler;
import com.nikoarap.quotebuddy.utils.PopulateRecyclerView;
import com.nikoarap.quotebuddy.adapters.QuotesAdapter;
import com.nikoarap.quotebuddy.db.AppDao;
import com.nikoarap.quotebuddy.db.AppDatabase;
import com.nikoarap.quotebuddy.models.Quotes;
import com.nikoarap.quotebuddy.utils.VerticalSpacingDecorator;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FavouritesFragment extends Fragment implements QuotesAdapter.OnQuoteListener {

    private AppDao appDao;
    private RecyclerView recView;
    private ArrayList<Quotes> quotesList = new ArrayList<>();
    private PopulateRecyclerView populateRecyclerView;
    private VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(10);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        recView = root.findViewById(R.id.quotesRecyclerView);
        recView.setNestedScrollingEnabled(false);
        recView.addItemDecoration(itemDecorator);

        //Initialize the db
        Context context = FavouritesFragment.this.getActivity();
        assert context != null;
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        appDao = appDatabase.getAppDao();

        observeFromDb();

        //attach swipe & touch callbacks to ListItems in RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recView);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    //observing data changed from the DB
    private void observeFromDb(){
        appDao.getFavdQuotes().observe(this, (Quotes[] quotes) -> {
            if (quotes != null){
                    quotesList.addAll(Arrays.asList(quotes));
                    populateRecyclerView = new PopulateRecyclerView(Objects.requireNonNull(getActivity()),recView);
                    populateRecyclerView.populate(quotes,this);
                }

        });
    }

    //delete quote by swiping to the right
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder,
                              @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteFavQuoteTask(quotesList.get(viewHolder.getAdapterPosition()));
        }
    };

    private void deleteFavQuoteTask(Quotes favQuote) {
        new DeleteFavQuoteHandler(appDao,getActivity()).executeHandler(favQuote);
    }

    @Override
    public void onQuoteClick(int position) {

    }
}