package com.example.client.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.adapters.BrowseProductsAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.entitymodels.user.User;
import com.example.client.entitymodels.user.UserDetails;
import com.example.client.fragments.Portrait;
import com.example.client.viewmodels.UserProfileViewModel;
import com.example.client.webservices.IWishListWebService;
import com.example.client.webservices.RetrofitSingleton;
import com.google.android.gms.common.FirstPartyScopes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    TextView txtEmail, txtName;
    FloatingActionButton fab;
    ListView wishList;
    BrowseProductsAdapter adapter;
    List<Product> products;
    IWishListWebService wishListWebService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.idFloatingButton);
        NavigationView navigationView = findViewById(R.id.nav_view);
        wishListWebService = RetrofitSingleton.getInstance().create(IWishListWebService.class);
        wishList = findViewById(R.id.lvWishList);


        View headerView = navigationView.getHeaderView(0);
        txtEmail=headerView.findViewById(R.id.txtDEmail);
        txtName=headerView.findViewById(R.id.txtDName);



        //get user
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("user");
        setUserDetails(user);

        sp = getSharedPreferences("userId", MODE_PRIVATE);
        getWishListItems(sp.getInt("userId", MODE_PRIVATE));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scannow();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setOnClickWish();
    }

    void setUserDetails(User user) {
        UserDetails ud = user.getUserDetails();
        String firstName = ud.getFirstName();
        String lastName = ud.getLastName();
        String name = firstName + " " + lastName;
        String email = user.getEmail();
        txtName.setText(name);
        txtEmail.setText(email);
    }

    void setOnClickWish(){
        wishList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogDelete((int)products.get(position).getProductId());
                return false;
            }
        });
    }

    public void AlertDialogDelete(final int productId){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(DashboardActivity.this);
        dialogBuilder.setMessage("Do you want to delete this item?");
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.RemoveItemFromWishList(getUserId(), productId);
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_cart:
                Intent intent=new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            sp = getSharedPreferences("userId", MODE_PRIVATE);
            editor = sp.edit();
            editor.putInt("userId", 0);
            editor.commit();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else if( id == R.id.nav_browse_products){
            Intent intent = new Intent(DashboardActivity.this, BrowseProducsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.purchase_history){
            Intent intent = new Intent(DashboardActivity.this, PurchaseHistoryActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void scannow(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Portrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan your barcode");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()== null){
                Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent(getApplicationContext(), BarcodeActivity.class);
                intent.putExtra("barcode",result.getContents());
                startActivity(intent);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getWishListItems(int userId){
        wishListWebService.getWishlistItems(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                adapter = new BrowseProductsAdapter(DashboardActivity.this,products);
                wishList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        sp = getSharedPreferences("userId", MODE_PRIVATE);
        getWishListItems(sp.getInt("userId", MODE_PRIVATE));
    }

    private int getUserId(){
        sp = getSharedPreferences("userId", MODE_PRIVATE);
        return sp.getInt("userId", MODE_PRIVATE);
    }
}
