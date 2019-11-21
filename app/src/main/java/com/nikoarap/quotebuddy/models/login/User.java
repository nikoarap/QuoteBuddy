package com.nikoarap.quotebuddy.models.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//POJO class for user login
public class User implements Parcelable {

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(String login, String password) {
        this.password = password;
        this.login = login;
    }


    protected User(Parcel in) {
        password = in.readString();
        login = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getLogin ()
    {
        return login;
    }

    public void setLogin (String login)
    {
        this.login = login;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(password);
        dest.writeString(login);
    }
}
