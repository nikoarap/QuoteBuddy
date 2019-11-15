package com.nikoarap.favqsapp.api;

import com.nikoarap.favqsapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//base class of retrofit
public class RetrofitRequestClass {

    //retrofit instantiation
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static FetchJSONDataAPI fetchJSONDataAPI = retrofit.create(FetchJSONDataAPI.class);

    //request interface
    public static FetchJSONDataAPI fetchApi() {
        return fetchJSONDataAPI;
    }


}


