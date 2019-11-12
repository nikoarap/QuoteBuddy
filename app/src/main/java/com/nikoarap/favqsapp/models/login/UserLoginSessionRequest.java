package com.nikoarap.favqsapp.models.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserLoginSessionRequest implements Parcelable {

    @SerializedName("user")
    private User user;

    public UserLoginSessionRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public UserLoginSessionRequest(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserLoginSessionRequest> CREATOR = new Creator<UserLoginSessionRequest>() {
        @Override
        public UserLoginSessionRequest createFromParcel(Parcel in) {
            return new UserLoginSessionRequest(in);
        }

        @Override
        public UserLoginSessionRequest[] newArray(int size) {
            return new UserLoginSessionRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }
}
