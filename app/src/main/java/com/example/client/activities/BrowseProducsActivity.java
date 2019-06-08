package com.example.client.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.adapters.BrowseProductsAdapter;
import com.example.client.adapters.ProductListAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.IProductWebservice;
import com.example.client.webservices.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseProducsActivity extends AppCompatActivity {

    private ListView listviewBrowseProducts;
    private List<Product> productList;
    private ListAdapter listAdapter;
    private IProductWebservice productWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_producs);
        setControls();
        productWebservice=RetrofitSingleton.getInstance().create(IProductWebservice.class);

        productWebservice.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList=new ArrayList<>();
                if(response.isSuccessful()){
                    productList=response.body();
                    listAdapter=new BrowseProductsAdapter(BrowseProducsActivity.this,productList);
                    listviewBrowseProducts.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    private void setControls() {
        listviewBrowseProducts=findViewById(R.id.listview_browse_products);
    }
}
