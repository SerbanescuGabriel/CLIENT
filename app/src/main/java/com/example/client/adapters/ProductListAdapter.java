package com.example.client.adapters;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
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
    int userId;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        cartWebservice = RetrofitSingleton.getInstance().create(ICartWebservice.class);
        sharedPreferences =  mContext.getSharedPreferences("userId", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", MODE_PRIVATE);
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
        TextView txtManufacturerName=v.findViewById(R.id.txtManufacturerNameC);
        TextView txtCategoryName=v.findViewById(R.id.txtCategoryNameC);
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
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = (int)mProductList.get(position).getProductId();
                subtractItem(productId);
            }
        });

        //setText for textView
        txtProductName.setText(mProductList.get(position).getProductName());
        txtManufacturerName.setText(mProductList.get(position).getManufacturerName());
        txtCategoryName.setText(mProductList.get(position).getCategoryName());
        txtPrice.setText(String.valueOf(mProductList.get(position).getPrice() * mProductList.get(position).getQuantity()) +" RON");
        txtQuantity.setText(String.valueOf(mProductList.get(position).getQuantity()));

        //save product id to tag
        v.setTag(mProductList.get(position).getProductId());

        return v;
    }

    private void addItem(int productId){
        cartWebservice.Plus(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void subtractItem(int productId){
        cartWebservice.Minus(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        getItems();
    }

    private void getItems(){
        cartWebservice.getCartProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> cart=new ArrayList<>();

                if(response.isSuccessful()){
                    mProductList = response.body();
                    //todo
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //todo
            }
        });
    }
}
