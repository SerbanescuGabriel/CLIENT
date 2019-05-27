package com.example.client.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.adapters.ProductListAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private TextView txtCart;

    private ICartWebservice cartWebservice;

    private ListView listViewProducts;
    private ListAdapter adapter;
   // private List<Product> mProductList;


    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setControl();

        cartWebservice= RetrofitSingleton.getInstance().create(ICartWebservice.class);

        getCartItems();
    }

    private void getCartItems() {
        sp=getSharedPreferences("userId", MODE_PRIVATE);
        int userId=sp.getInt("userId",MODE_PRIVATE);
        cartWebservice.getCartProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> cart=new ArrayList<>();

                if(response.isSuccessful()){
                    cart=response.body();

                    adapter=new ProductListAdapter(getApplicationContext(),cart);
                    listViewProducts.setAdapter(adapter);

                    //

                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void setControl() {

        listViewProducts=findViewById(R.id.listview_product);

    }


}
