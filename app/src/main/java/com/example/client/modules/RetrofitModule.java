package com.example.client.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    public static final String BASE_URL = "http://10.0.2.2:49807/api/";
    //public static final String BASE_URL = ""; //base url Iulia

    @Provides
    @Singleton
    public static Retrofit provideRetrofit() {

        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
}
