package com.example.client.webservices.userWebservice;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.client.entitymodels.user.User;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserWebservice {
    private Retrofit retrofitInstance;
    IUserWebservice apiCaller;

    int userId;

    @Inject
    public UserWebservice(Retrofit retrofitInstance) {
        this.retrofitInstance = retrofitInstance;
        apiCaller = retrofitInstance.create(IUserWebservice.class);
        userId = 0;
    }

    public void TestLogin(){
        Log.i("Test", "This is a message from injected UserWebservice");
    }

    public void Login(String username, String password){
        final MutableLiveData<Integer> _userId = new MutableLiveData<>();

        apiCaller.login(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Err", "error");
            }
        });
    }


}
