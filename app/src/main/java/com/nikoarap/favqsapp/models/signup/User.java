package com.nikoarap.favqsapp.models.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class User implements Parcelable {

    @SerializedName("login")
    private String login;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public User(String login, String email, String password) {
        this.password = password;
        this.login = login;
        this.email = email;
    }

    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private User(Parcel in) {
        password = in.readString();
        login = in.readString();
        email = in.readString();
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
        dest.writeString(email);
    }
}
