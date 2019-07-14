package com.example.client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.adapters.PurchaseHistoryAdapter;
import com.example.client.entitymodels.cart.Cart;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryActivity extends AppCompatActivity {

    ListView lvPurchaseHistory;
    ICartWebservice cartWebservice;
    PurchaseHistoryAdapter adapter;
    List<Cart> cartList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        lvPurchaseHistory = findViewById(R.id.lvPurchaseHistory);
        cartWebservice = RetrofitSingleton.getInstance().create(ICartWebservice.class);
        SetOnClickOnLv();
        PopulateListView();
    }

    private void SetOnClickOnLv() {
        lvPurchaseHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PurchaseHistoryActivity.this, PurchaseHistoryItemsActivity.class);
                intent.putExtra("cartId", cartList.get(position).getCartId());
                startActivity(intent);
            }
        });
    }

    public void PopulateListView(){
        cartWebservice.GetPurchaseHistory(GetUserId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                cartList = response.body();
                adapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this, cartList);
                lvPurchaseHistory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int GetUserId(){
        sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", MODE_PRIVATE);
    }
}
