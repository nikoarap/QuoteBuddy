package com.nikoarap.favqsapp.api;

import com.nikoarap.favqsapp.models.login.LoginAuthorization;
import com.nikoarap.favqsapp.models.QuoteModel;
import com.nikoarap.favqsapp.models.login.UserLoginSessionRequest;
import com.nikoarap.favqsapp.models.signup.SignUpAuthorization;
import com.nikoarap.favqsapp.models.signup.UserSignupSessionRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//interface for server requests and API handling
public interface FetchJSONDataAPI {

    //get a random list of quotes
    @Headers({
            "Content-Type:application/json",
            "Authorization:Token token=9d040b9cfcd1c7718a22ae88577f9953"
    })
    @GET("quotes")
    Call<QuoteModel> getQuotesApi();

    //post login session
    @Headers({
            "Content-Type:application/json",
            "Authorization:Token token=9d040b9cfcd1c7718a22ae88577f9953"
    })
    @POST("session")
    Call<LoginAuthorization> loginAccount(
            @Body UserLoginSessionRequest loginSessionRequest
    );

    //post signup session
    @Headers({
            "Content-Type:application/json",
            "Authorization:Token token=9d040b9cfcd1c7718a22ae88577f9953"
    })
    @POST("users")
    Call<SignUpAuthorization> signUpAccount(
            @Body UserSignupSessionRequest signupSessionRequest
    );

    //get quotes by author
    @Headers({
            "Content-Type:application/json",
            "Authorization:Token token=9d040b9cfcd1c7718a22ae88577f9953"
    })
    @GET("quotes/")
    Call<QuoteModel> getQuotesByAuthor(
            @Query("filter") String authorName,
            @Query("&") String and,
            @Query("type") String author
    );

    //get quotes by word query in search
    @Headers({
            "Content-Type:application/json",
            "Authorization:Token token=9d040b9cfcd1c7718a22ae88577f9953"
    })
    @GET("quotes/")
    Call<QuoteModel> getQuotesByWord(
            @Query("filter") String word
    );

}
