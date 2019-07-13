package com.example.client.webservices;

import com.example.client.entitymodels.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICartWebservice {

    @POST("cart")
    @FormUrlEncoded
    Call<Boolean> addItemToCart(@Field("userId") int userId,@Field("productId") long productId, @Field("Quantity") int quantity);


    @GET("cart/{userId}")
    Call<List<Product>> getCartProducts(@Path("userId")int userId);


    @GET("cart/cartId/{userId}")
    Call<Long> getCartId(@Path("userId") int userId);

    @POST("cart/add")
    @FormUrlEncoded
    Call<Boolean> Plus(@Field("UserId") int userId, @Field("ProductId") int productId);

    @POST("cart/substract")
    @FormUrlEncoded
    Call<Boolean> Minus(@Field("UserId") int userId, @Field("ProductId") int productId);

    @POST("cart/remove")
    @FormUrlEncoded
    Call<Boolean> Remove(@Field("UserId") int userId, @Field("ProductId") int productId);
}
