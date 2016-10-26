package com.akash.applications.myfaceindia;

import android.net.Uri;

import com.akash.applications.myfaceindia.Models.LoginResponse;
import com.akash.applications.myfaceindia.Models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Amandeep on 10/26/2016.
 */

interface ApiInterface {
    //TODO Replace with actual API
    @GET("query-to-be used")
    Call<LoginResponse> authUser(@Query("username") String userName, @Query("password") String password);

    @POST("query-to-be-used")
    Call<RegisterResponse> registerUser(
            @Path("username")
                    String userName,

            @Path("password")
                    String password,

            @Path("email")
                    String email,

            @Path("captcha")
                    String captcha
    );


    @GET("get-captcha-query")
    Call<Uri> getCaptcha();
}
