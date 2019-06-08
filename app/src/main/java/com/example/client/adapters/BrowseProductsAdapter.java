package com.example.client.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;

import java.util.List;

public class BrowseProductsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mList;

    public BrowseProductsAdapter(Context mContext, List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        TextView txtManufacturerName=v.findViewById(R.id.txtManufacturerNameBP);
        TextView txtCategoryName=v.findViewById(R.id.txtCategoryNameBP);
        TextView txtPrice=v.findViewById(R.id.txtPriceBP);

        txtProductName.setText(mList.get(position).getProductName());
        txtManufacturerName.setText(mList.get(position).getManufacturerName());
        txtCategoryName.setText(mList.get(position).getCategoryName());
        txtPrice.setText(String.valueOf(mList.get(position).getPrice()));

        v.setTag(mList.get(position).getProductId());

        return v;
    }
}
