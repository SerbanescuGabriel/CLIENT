package com.example.client.webservices.userWebservice;

import com.example.client.entitymodels.user.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserWebservice {

    @POST("users/login")
    @FormUrlEncoded
    Call<User> login(@Field("Username") String username, @Field("Password") String password);
}
