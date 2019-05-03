package com.example.client.webservices.userWebservice;

import android.util.Log;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserWebservice {
    private Retrofit retrofitInstance;

    @Inject
    public UserWebservice(Retrofit retrofitInstance) {
        this.retrofitInstance = retrofitInstance;
    }

    public void TestLogin(){
        Log.i("Test", "This is a message from injected UserWebservice");
    }
}
