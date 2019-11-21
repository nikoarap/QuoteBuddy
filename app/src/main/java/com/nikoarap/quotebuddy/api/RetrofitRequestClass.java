package com.nikoarap.quotebuddy.api;

import com.nikoarap.quotebuddy.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//base class of retrofit
public class RetrofitRequestClass {

    private static RequestInterceptor requestInterceptor = new RequestInterceptor();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //creating an OkHttpClient with custom Interceptor for adding headers to requests automatically
    private static OkHttpClient setClientWithInterceptor() {
        httpClient.addInterceptor(requestInterceptor);
        return httpClient.build();
    }

    //retrofit instantiation
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(setClientWithInterceptor())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static FetchJSONDataAPI fetchJSONDataAPI = retrofit.create(FetchJSONDataAPI.class);

    //request interface
    public static FetchJSONDataAPI fetchApi() {
        return fetchJSONDataAPI;
    }

}




