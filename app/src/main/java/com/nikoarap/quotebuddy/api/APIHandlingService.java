package com.nikoarap.quotebuddy.api;

import com.nikoarap.quotebuddy.models.login.LoginAuthorization;
import com.nikoarap.quotebuddy.models.QuoteModel;
import com.nikoarap.quotebuddy.models.login.UserLoginSessionRequest;
import com.nikoarap.quotebuddy.models.signup.SignUpAuthorization;
import com.nikoarap.quotebuddy.models.signup.UserSignupSessionRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//interface for server requests and API handling
public interface APIHandlingService {

    //get a random list of quotes
    @GET("quotes")
    Call<QuoteModel> getQuotesApi();

    //post login session
    @POST("session")
    Call<LoginAuthorization> loginAccount(
            @Body UserLoginSessionRequest loginSessionRequest
    );

    //post signup session
    @POST("users")
    Call<SignUpAuthorization> signUpAccount(
            @Body UserSignupSessionRequest signupSessionRequest
    );

    //get quotes by author
    @GET("quotes/")
    Call<QuoteModel> getQuotesByAuthor(
            @Query("filter") String authorName,
            @Query("&") String and,
            @Query("type") String author
    );

    //get quotes by word query in search
    @GET("quotes/")
    Call<QuoteModel> getQuotesByWord(
            @Query("filter") String word
    );

}
