package com.example.client.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit instance;
    //private static final String BASE_URL="http://10.0.2.2:49807/api/";
    //private static String BASE_URL="http://10.0.2.2:49801/api/"; //iulia
   // private static String BASE_URL="http://192.168.100.19:48010/api/"; // IP Gabi
    private static String BASE_URL="http://192.168.0.15:48010/api/"; // IP Iulia
    //private static String BASE_URL="http://192.168.43.23:48010/api/"; // IP Telefon Iulia
    //private static String BASE_URL="http://192.168.43.23:48010/api/"; // IP Telefon Gabi

    public static synchronized Retrofit getInstance(){
        if(instance == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return instance;
    }

}
