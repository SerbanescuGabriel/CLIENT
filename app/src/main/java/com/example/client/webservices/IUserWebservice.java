package com.example.client.webservices;

import com.example.client.entitymodels.user.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserWebservice {
    @POST("users/login")
    @FormUrlEncoded
    Call<User> login(@Field("Username") String username, @Field("Password") String password);

    @POST("users")
    @FormUrlEncoded
    Call<User> register(@Field("Username") String username, @Field("Password") String password, @Field("Email") String email);

    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") int userId);
}
