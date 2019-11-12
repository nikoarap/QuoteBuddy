package com.nikoarap.favqsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

//quotemodel model -- quotemodel POJO class
public class QuoteModel implements Parcelable {

    private String last_page;

    private String page;

    private Quotes[] quotes;

    private QuoteModel(Parcel in) {
        last_page = in.readString();
        page = in.readString();
        quotes = in.createTypedArray(Quotes.CREATOR);
    }

    public static final Creator<QuoteModel> CREATOR = new Creator<QuoteModel>() {
        @Override
        public QuoteModel createFromParcel(Parcel in) {
            return new QuoteModel(in);
        }

        @Override
        public QuoteModel[] newArray(int size) {
            return new QuoteModel[size];
        }
    };

    public Quotes[] getQuotes ()
    {
        return quotes;
    }

    public void setQuotes (Quotes[] quotes)
    {
        this.quotes = quotes;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "ClassPojo [last_page = "+last_page+", page = "+page+", quotes = "+ Arrays.toString(quotes) +"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(last_page);
        dest.writeString(page);
        dest.writeTypedArray(quotes, flags);
    }
}
