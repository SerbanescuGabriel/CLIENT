package com.example.client.webservices;

import com.example.client.entitymodels.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWishListWebService {
    @POST("wishlist")
    @FormUrlEncoded
    Call<Boolean> addItemToWishlist(@Field("userId") int userId, @Field("productId") long productId);

    @GET("wishlist/{userId}")
    Call<List<Product>> getWishlistItems(@Path("userId") int userId);

    @POST("wishlist/remove")
    @FormUrlEncoded
    Call<Boolean> removeItemFromWishlist(@Field("userId") int userId, @Field("productId") long productId);

}
