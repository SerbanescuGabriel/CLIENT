package com.example.client.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.IWishListWebService;
import com.example.client.webservices.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseProductsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mList;
    IWishListWebService wishListWebService;

    public BrowseProductsAdapter(Context mContext, List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
        wishListWebService = RetrofitSingleton.getInstance().create(IWishListWebService.class);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(mContext, R.layout.item_browse_product_list,null);
        TextView txtProductName=v.findViewById(R.id.txtProductNameBP);
        //TextView txtManufacturerName=v.findViewById(R.id.txtManufacturerNameBP);
        //TextView txtCategoryName=v.findViewById(R.id.txtCategoryNameBP);
        TextView txtPrice=v.findViewById(R.id.txtPriceBP);

        txtProductName.setText(mList.get(position).getProductName());
        //txtManufacturerName.setText(mList.get(position).getManufacturerName());
        //txtCategoryName.setText(mList.get(position).getCategoryName());
        txtPrice.setText("Price: " + String.valueOf(mList.get(position).getPrice()) + " RON");

        v.setTag(mList.get(position).getProductId());

        return v;
    }

    public void UpdateList(List<Product> filteredList){
        mList = new ArrayList<Product>();
        mList = filteredList;
        notifyDataSetChanged();
    }

    public void UpdateDashboardWishListItems(int userId){
        wishListWebService.getWishlistItems(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mList = response.body();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public void RemoveItemFromWishList(int userId, final int productId){
        wishListWebService.removeItemFromWishlist(userId, productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Product product = new Product();

                for(Product p:mList){
                    if(p.getProductId() == productId){
                        product = p;
                    }
                }
                mList.remove(product);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}
