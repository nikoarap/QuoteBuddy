package com.nikoarap.quotebuddy.models.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

//POJO class for sign up request
public class UserSignupSessionRequest implements Parcelable {

    @SerializedName("user")
    private User user;

    public UserSignupSessionRequest(User user) {
        this.user = user;
    }

    private UserSignupSessionRequest(Parcel in) {
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

    @NotNull
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
