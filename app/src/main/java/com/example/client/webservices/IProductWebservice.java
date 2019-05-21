package com.example.client.webservices;

import com.example.client.entitymodels.product.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IProductWebservice {

    @GET("products/{barCode}")
    Call<Product> getProductByBarcode(@Path("barCode") String barcode);
}
