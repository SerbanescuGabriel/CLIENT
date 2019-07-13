package com.example.client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.IProductWebservice;
import com.example.client.webservices.IWishListWebService;
import com.example.client.webservices.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeActivity extends AppCompatActivity {


    private TextView txtProductName, txtProductManufacturer,txtProductCategory,txtPrice, txtQtty;
    private Button btnCancel, btnAddToCart, btnPlusQtty, btnMinusQtty;

    private IProductWebservice productWebservice;
    private ICartWebservice cartWebservice;

    private Product product;
    private SharedPreferences sp;
    IWishListWebService wishListWebService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        Intent intent=getIntent();
        String barcode=intent.getStringExtra("barcode");

        productWebservice= RetrofitSingleton.getInstance().create(IProductWebservice.class);
        cartWebservice=RetrofitSingleton.getInstance().create(ICartWebservice.class);

        bindControls();
        populateControls(barcode);
        setClicks();
        wishListWebService = RetrofitSingleton.getInstance().create(IWishListWebService.class);
    }

     void populateControls(String barcode){

        productWebservice.getProductByBarcode(barcode).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful()){
                    product=response.body();
                    txtProductName.setText(product.getProductName());
                    txtPrice.setText(Float.toString(product.getPrice()));
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

     }

     void bindControls(){
        txtProductName=findViewById(R.id.txtProductName);
        txtPrice=findViewById(R.id.txtPrice);
        btnAddToCart=findViewById(R.id.btnAddToCart);
        btnCancel=findViewById(R.id.btnCancel);
        btnPlusQtty = findViewById(R.id.btnPlusQttyBarCode);
        btnMinusQtty = findViewById(R.id.btnMinusQttyBarCode);
        txtQtty = findViewById(R.id.txtQuantityBarCode);
     }

     void setClicks(){
         btnAddToCart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 long idProduct=product.getProductId();
                 int quantity = Integer.parseInt(txtQtty.getText().toString());
                 cartWebservice.addItemToCart(getUserId(), idProduct, quantity).enqueue(new Callback<Boolean>() {
                     @Override
                     public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                         if(response.body()){
                             wishListWebService.removeItemFromWishlist(getUserId(), (int)product.getProductId()).enqueue(new Callback<Boolean>() {
                                 @Override
                                 public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                 }

                                 @Override
                                 public void onFailure(Call<Boolean> call, Throwable t) {

                                 }
                             });
                             finish();
                         }
                     }

                     @Override
                     public void onFailure(Call<Boolean> call, Throwable t) {
                         Toast.makeText(getApplicationContext(),"item NOT added to cart",Toast.LENGTH_LONG).show();
                     }
                 });
             }
         });

         btnPlusQtty.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int value = Integer.parseInt(txtQtty.getText().toString());
                 value++;
                 txtQtty.setText(Integer.toString(value));
             }
         });

         btnMinusQtty.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int value = Integer.parseInt(txtQtty.getText().toString());
                 value--;
                 txtQtty.setText(Integer.toString(value));
             }
         });
     }

     private int getUserId(){
         sp=getSharedPreferences("userId", MODE_PRIVATE);
         return sp.getInt("userId",MODE_PRIVATE);
     }
}
