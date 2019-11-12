package com.nikoarap.favqsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotes")
public class Quotes implements Parcelable {

    @ColumnInfo(name = "private")
    @SerializedName("private")
    private String _private;

    @ColumnInfo(name = "favorites_count")
    @SerializedName("favorites_count")
    private String favorites_count;

    @ColumnInfo(name = "author")
    @SerializedName("author")
    private String author;

    @ColumnInfo(name = "dialogue")
    @SerializedName("dialogue")
    private String dialogue;

    @ColumnInfo(name = "upvotes_count")
    @SerializedName("upvotes_count")
    private String upvotes_count;

    @ColumnInfo(name = "author_permalink")
    @SerializedName("author_permalink")
    private String author_permalink;

    @NonNull
    @PrimaryKey
    @OnConflictStrategy
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "body")
    @SerializedName("body")
    private String body;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "tags")
    @SerializedName("tags")
    private String[] tags;

    @ColumnInfo(name = "downvotes_count")
    @SerializedName("downvotes_count")
    private String downvotes_count;

    public Quotes(String _private, String favorites_count, String author, String dialogue,
                  String upvotes_count, String author_permalink, @NotNull String id, String body, String url,
                  String[] tags, String downvotes_count) {
        this._private = _private;
        this.favorites_count = favorites_count;
        this.author = author;
        this.dialogue = dialogue;
        this.upvotes_count = upvotes_count;
        this.author_permalink = author_permalink;
        this.id = id;
        this.body = body;
        this.url = url;
        this.tags = tags;
        this.downvotes_count = downvotes_count;
    }

    protected Quotes(Parcel in) {
        _private = in.readString();
        favorites_count = in.readString();
        author = in.readString();
        dialogue = in.readString();
        upvotes_count = in.readString();
        author_permalink = in.readString();
        id = Objects.requireNonNull(in.readString());
        body = in.readString();
        url = in.readString();
        tags = in.createStringArray();
        downvotes_count = in.readString();
    }

    public static final Creator<Quotes> CREATOR = new Creator<Quotes>() {
        @Override
        public Quotes createFromParcel(Parcel in) {
            return new Quotes(in);
        }

        @Override
        public Quotes[] newArray(int size) {
            return new Quotes[size];
        }
    };

    public String getPrivate ()
    {
        return _private;
    }

    public void setPrivate (String _private)
    {
        this._private = _private;
    }

    public String getFavorites_count ()
    {
        return favorites_count;
    }

    public String getAuthor ()
    {
        return author;
    }

    public String getDialogue ()
    {
        return dialogue;
    }

    public String getUpvotes_count ()
    {
        return upvotes_count;
    }

    public String getAuthor_permalink ()
    {
        return author_permalink;
    }

    @NotNull
    public String getId ()
    {
        return id;
    }

    public void setId (@NotNull String id)
    {
        this.id = id;
    }

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getUrl ()
    {
        return url;
    }

    public String[] getTags ()
    {
        return tags;
    }

    public String getDownvotes_count ()
    {
        return downvotes_count;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "ClassPojo [private = "+_private+", favorites_count = "+favorites_count+", author = "+author+", " +
                "dialogue = "+dialogue+", upvotes_count = "+upvotes_count+", author_permalink = "+author_permalink+", " +
                "id = "+id+", body = "+body+", url = "+url+", tags = "+ Arrays.toString(tags) +", downvotes_count = "+downvotes_count+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_private);
        dest.writeString(favorites_count);
        dest.writeString(author);
        dest.writeString(dialogue);
        dest.writeString(upvotes_count);
        dest.writeString(author_permalink);
        dest.writeString(id);
        dest.writeString(body);
        dest.writeString(url);
        dest.writeStringArray(tags);
        dest.writeString(downvotes_count);
    }
}
