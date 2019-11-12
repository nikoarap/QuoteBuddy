package com.nikoarap.favqsapp.models.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserSignupSessionRequest implements Parcelable {

    @SerializedName("user")
    private User user;

    public UserSignupSessionRequest(User user) {
        this.user = user;
    }

    protected UserSignupSessionRequest(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserSignupSessionRequest> CREATOR = new Creator<UserSignupSessionRequest>() {
        @Override
        public UserSignupSessionRequest createFromParcel(Parcel in) {
            return new UserSignupSessionRequest(in);
        }

        @Override
        public UserSignupSessionRequest[] newArray(int size) {
            return new UserSignupSessionRequest[size];
        }
    };


    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }
}
