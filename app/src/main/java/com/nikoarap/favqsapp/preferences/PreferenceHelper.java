package com.nikoarap.favqsapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String TOKEN = "token";
    private final String LOGIN = "login";
    private final String EMAIL = "email";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void setToken(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(TOKEN, loginorout);
        edit.commit();
    }
    public boolean getToken() {
        return app_prefs.getBoolean(TOKEN, false);
    }

    public void setLogin(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(LOGIN, loginorout);
        edit.commit();
    }
    public String getLogin() {
        return app_prefs.getString(LOGIN, "");
    }

    public void setEmail(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, loginorout);
        edit.commit();
    }
    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }

}
