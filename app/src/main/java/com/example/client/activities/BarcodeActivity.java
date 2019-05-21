package com.example.client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.IProductWebservice;
import com.example.client.webservices.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeActivity extends AppCompatActivity {


    private TextView txtProductName, txtProductManufacturer,txtProductCategory,txtPrice;
    private Button btnCancel, btnAddToCart;

    private IProductWebservice productWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        Intent intent=getIntent();
        String barcode=intent.getStringExtra("barcode");

        productWebservice= RetrofitSingleton.getInstance().create(IProductWebservice.class);

        bindControls();
        populateControls(barcode);


    }

     void populateControls(String barcode){

        productWebservice.getProductByBarcode(barcode).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful()){

                    Product product=response.body();
                    txtProductName.setText(product.getProductName());
                    txtProductManufacturer.setText(product.getManufacturerName());
                    txtProductCategory.setText(product.getCategoryName());
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
        txtProductManufacturer=findViewById(R.id.txtManufacturerName);
        txtProductCategory=findViewById(R.id.txtCategoryName);
        txtPrice=findViewById(R.id.txtPrice);
        btnAddToCart=findViewById(R.id.btnAddToCart);
        btnCancel=findViewById(R.id.btnCancel);

     }

}
