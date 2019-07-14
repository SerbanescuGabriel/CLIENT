package com.example.client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.client.R;
import com.example.client.adapters.PurchaseHistoryItemsAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryItemsActivity extends AppCompatActivity {

    private int cartId;
    private PurchaseHistoryItemsAdapter adapter;
    private List<Product> productList;
    private ListView lvPurchaseHistoryItems;
    private ICartWebservice cartWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history_items);

        Intent intent = getIntent();
        cartId = intent.getIntExtra("cartId", 0);

        lvPurchaseHistoryItems = findViewById(R.id.lvPurchaseHistoryItems);

        cartWebservice = RetrofitSingleton.getInstance().create(ICartWebservice.class);
        PopulateListView();
    }

    private void PopulateListView(){
        cartWebservice.GetCartItemsByCartId(cartId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                adapter = new PurchaseHistoryItemsAdapter(PurchaseHistoryItemsActivity.this, productList);
                lvPurchaseHistoryItems.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}
