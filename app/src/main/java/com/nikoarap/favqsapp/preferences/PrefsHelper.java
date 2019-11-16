package com.nikoarap.favqsapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nikoarap.favqsapp.models.login.LoginAuthorization;

import java.util.Objects;

import retrofit2.Response;

public class PrefsHelper {

    private SharedPreferences prefs;

    public String getStringfromPrefs(String key, Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, "defaultStringIfNothingFound");
    }

    public void deleteStringfromPrefs(String key, Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().remove(key).apply();
    }

    public void saveLoginData(Response<LoginAuthorization> response, Context context){
        String tokenResponse = Objects.requireNonNull(response.body()).getToken();
        String loginResponse = response.body().getLogin();
        String emailResponse = response.body().getEmail();
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("token", tokenResponse);
        prefEditor.putString("login", loginResponse);
        prefEditor.putString("email", emailResponse);
        prefEditor.apply();
    }






}
