package com.example.client.webservices.userWebservice;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserWebservice {

    @POST("login")
    @FormUrlEncoded
    Call<Integer> login(@Field("Username") String username, @Field("Password") String password);
}
