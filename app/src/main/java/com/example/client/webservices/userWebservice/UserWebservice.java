package com.example.client.webservices.userWebservice;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserWebservice {
    private Retrofit retrofit;
    public static final String BASE_URL = "http://10.0.2.2:49800/api/users/";
    private IUserWebservice apiCaller;
    private int userId=-1;

    public UserWebservice(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiCaller = this.retrofit.create(IUserWebservice.class);
    }

    public int Login(String username, String password){

        try{
            apiCaller.login(username,password).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(!response.isSuccessful()){
                        return;
                    }

                    userId=Integer.parseInt(response.body().toString());

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.i("LoginError",t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return userId;
    }
}
