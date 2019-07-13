package com.example.client.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.adapters.BrowseProductsAdapter;
import com.example.client.adapters.ProductListAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.IProductWebservice;
import com.example.client.webservices.IWishListWebService;
import com.example.client.webservices.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseProducsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listviewBrowseProducts;
    private List<Product> productList;
    private BrowseProductsAdapter listAdapter;
    private IProductWebservice productWebservice;
    private IWishListWebService wishListWebService;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_producs);
        setControls();
        productWebservice=RetrofitSingleton.getInstance().create(IProductWebservice.class);
        wishListWebService = RetrofitSingleton.getInstance().create(IWishListWebService.class);
        sp = getSharedPreferences("userId", MODE_PRIVATE);

        getAllProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seach_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void setControls() {
        listviewBrowseProducts=findViewById(R.id.listview_browse_products);
        listviewBrowseProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addItemToWishList(productList.get(position).getProductId());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Product> filteredList = new ArrayList<Product>();
        for(Product product:productList){
            if(product.getProductName().toLowerCase().contains(newText)||
                    product.getCategoryName().toLowerCase().contains(newText)||
                    product.getManufacturerName().toLowerCase().contains(newText)){
                filteredList.add(product);
            }

            if(filteredList.size() > 0){
                listAdapter.UpdateList(filteredList);
            }
        }
        return true;
    }

    private void addItemToWishList(long productId){
        int userId = sp.getInt("userId", MODE_PRIVATE);
        wishListWebService.addItemToWishlist(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(), "Item added to wishlist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllProducts(){
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

}
