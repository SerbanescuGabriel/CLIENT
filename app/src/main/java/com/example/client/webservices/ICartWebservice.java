package com.example.client.webservices;

import com.example.client.entitymodels.product.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ICartWebservice {

    @POST("cart")
    @FormUrlEncoded
    Call<Boolean> addItemToCart(@Field("userId") int userId,@Field("productId") long productId);


}
