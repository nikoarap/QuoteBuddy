package com.nikoarap.favqsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class QuoteModel implements Parcelable {

    private String last_page;

    private String page;

    private Quotes[] quotes;

    public QuoteModel(String last_page, String page, Quotes[] quotes) {
        this.last_page = last_page;
        this.page = page;
        this.quotes = quotes;
    }

    protected QuoteModel(Parcel in) {
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

    public String getLast_page ()
    {
        return last_page;
    }

    public void setLast_page (String last_page)
    {
        this.last_page = last_page;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public Quotes[] getQuotes ()
    {
        return quotes;
    }

    public void setQuotes (Quotes[] quotes)
    {
        this.quotes = quotes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [last_page = "+last_page+", page = "+page+", quotes = "+quotes+"]";
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
