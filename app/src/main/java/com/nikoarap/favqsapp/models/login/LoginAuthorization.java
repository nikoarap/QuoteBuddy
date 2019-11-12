package com.nikoarap.favqsapp.models.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginAuthorization implements Parcelable {

    @SerializedName("User-Token")
    @Expose
    private String token;

    @SerializedName("login")
    private String login;

    @SerializedName("email")
    private String email;

    @SerializedName("error_code")
    private Integer error_code;

    @SerializedName("message")
    private String message;

    protected LoginAuthorization(Parcel in) {
        token = in.readString();
        login = in.readString();
        email = in.readString();
        if (in.readByte() == 0) {
            error_code = null;
        } else {
            error_code = in.readInt();
        }
        message = in.readString();
    }

    public static final Creator<LoginAuthorization> CREATOR = new Creator<LoginAuthorization>() {
        @Override
        public LoginAuthorization createFromParcel(Parcel in) {
            return new LoginAuthorization(in);
        }

        @Override
        public LoginAuthorization[] newArray(int size) {
            return new LoginAuthorization[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(login);
        dest.writeString(email);
        if (error_code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(error_code);
        }
        dest.writeString(message);
    }
}
