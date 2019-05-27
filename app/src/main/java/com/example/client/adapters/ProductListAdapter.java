package com.example.client.adapters;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mProductList;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v= View.inflate(mContext, R.layout.item_product_list,null);
        TextView txtProductName=v.findViewById(R.id.txtProductNameC);
        TextView txtManufacturerName=v.findViewById(R.id.txtManufacturerNameC);
        TextView txtCategoryName=v.findViewById(R.id.txtCategoryNameC);
        TextView txtPrice=v.findViewById(R.id.txtPriceC);

        //setText for textView
        txtProductName.setText(mProductList.get(position).getProductName());
        txtManufacturerName.setText(mProductList.get(position).getManufacturerName());
        txtCategoryName.setText(mProductList.get(position).getCategoryName());
        txtPrice.setText(String.valueOf(mProductList.get(position).getPrice())+" RON");

        //save product id to tag
        v.setTag(mProductList.get(position).getProductId());

        return v;
    }
}
