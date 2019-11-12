package com.nikoarap.favqsapp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class Converters {

    // converts the type String to type Address (Address model)
    @TypeConverter
    public static String[] fromString(String value){
        Type arrayType = new TypeToken<String[]>(){}.getType();
        return new Gson().fromJson(value, arrayType);
    }

    // converts the type Address (Address model) to type String
    @TypeConverter
    public static String fromArrayList(String[] strarr){
        Gson gson  = new Gson();
        return gson.toJson(strarr);
    }
}
