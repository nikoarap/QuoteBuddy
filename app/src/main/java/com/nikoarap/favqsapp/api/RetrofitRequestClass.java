package com.nikoarap.favqsapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestClass {

    //retrofit instantiation
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static FetchJSONDataAPI fetchJSONDataAPI = retrofit.create(FetchJSONDataAPI.class);

    public static FetchJSONDataAPI fetchApi() {
        return fetchJSONDataAPI;
    }


}


