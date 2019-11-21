package com.nikoarap.quotebuddy.models.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//POJO class for sign up response body from server
public class SignUpAuthorization implements Parcelable {

    @SerializedName("User-Token")
    @Expose
    private String token;

    @SerializedName("login")
    private String login;

    @SerializedName("error_code")
    private Integer error_code;

    @SerializedName("message")
    private String message;


    private SignUpAuthorization(Parcel in) {
        token = in.readString();
        login = in.readString();
        if (in.readByte() == 0) {
            error_code = null;
        } else {
            error_code = in.readInt();
        }
        message = in.readString();
    }

    public static final Creator<SignUpAuthorization> CREATOR = new Creator<SignUpAuthorization>() {
        @Override
        public SignUpAuthorization createFromParcel(Parcel in) {
            return new SignUpAuthorization(in);
        }

        @Override
        public SignUpAuthorization[] newArray(int size) {
            return new SignUpAuthorization[size];
        }
    };

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getError_code() {
        return error_code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(login);
        if (error_code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(error_code);
        }
        dest.writeString(message);
    }
}
