package com.akash.applications.myfaceindia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amandeep on 10/26/2016.
 */

public interface ApiInterface {
    //TODO Replace with actual API
    @GET("query-to-be used")
    Call<LoginResponse> authUser(@Query("username") String userName, @Query("password") String password);
}
