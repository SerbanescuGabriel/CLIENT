package com.example.client.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.activities.CartActivity;
import com.example.client.activities.DashboardActivity;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mProductList;
    ICartWebservice cartWebservice;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    int userId;
    View cartView;
    TextView etPrice;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        cartWebservice = RetrofitSingleton.getInstance().create(ICartWebservice.class);
        sharedPreferences =  mContext.getSharedPreferences("userId", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", MODE_PRIVATE);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        cartView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        etPrice = cartView.findViewById(R.id.etTotalPriceCart);
    }

    @Override
    public int getCount() {
        return  mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= View.inflate(mContext, R.layout.item_product_list,null);
        TextView txtProductName=v.findViewById(R.id.txtProductNameC);
        TextView txtPrice=v.findViewById(R.id.txtPriceC);
        final TextView txtQuantity = v.findViewById(R.id.txtQuantity);
        Button btnPlus, btnMinus;
        btnPlus = v.findViewById(R.id.btnPlusQtty);
        btnMinus = v.findViewById(R.id.btnMinusQtty);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = (int)mProductList.get(position).getProductId();
                addItem(productId);
                progressDialog.show();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = (int)mProductList.get(position).getProductId();
                subtractItem(productId);
                progressDialog.show();
            }
        });

        txtProductName.setText(mProductList.get(position).getProductName());
        txtPrice.setText("Price: "+ String.valueOf(mProductList.get(position).getPrice() * mProductList.get(position).getQuantity()) +" RON");
        txtQuantity.setText(String.valueOf(mProductList.get(position).getQuantity()));

        v.setTag(mProductList.get(position).getProductId());

        return v;
    }

    private void addItem(int productId){
        cartWebservice.Plus(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                getItems();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void subtractItem(final int productId){
        cartWebservice.Minus(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    getItems();
                }else{
                    AlertDialogDelete(productId);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void getItems(){
        cartWebservice.getCartProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> cart=new ArrayList<>();

                if(response.isSuccessful()){
                    mProductList = response.body();
                    SetPrice();
                    notifyDataSetChanged();
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //todo
            }
        });
    }

    public void AlertDialogDelete(final int productId){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(mContext);
        dialogBuilder.setMessage("Do you want to delete this item?");
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteCartItem(userId, productId);
                dialog.dismiss();
                progressDialog.cancel();
            }
        });

        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                progressDialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void DeleteCartItem(int userId, final int productId){
        cartWebservice.Remove(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int position = 0;

                for(int i = 0; i<mProductList.size(); i++){
                    if(mProductList.get(i).getProductId() == productId){
                        position = i;
                    }
                }

                mProductList.remove(position);
                SetPrice();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void SetPrice(){
        float total = 0;
        for(Product product: mProductList){
            total+= product.getQuantity() * product.getPrice();
        }
        etPrice.setText("Your total price is: " + total + " RON");
    }
}
