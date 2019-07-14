package com.example.client.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.entitymodels.product.Product;

import java.util.List;

public class PurchaseHistoryItemsAdapter extends BaseAdapter {
    private List<Product> mProductList;
    private Context mContext;

    public PurchaseHistoryItemsAdapter(Context mContext, List<Product> mProductList){
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
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
        View v=View.inflate(mContext, R.layout.item_browse_product_list,null);
        TextView txtProductName=v.findViewById(R.id.txtProductNameBP);
        TextView txtPrice=v.findViewById(R.id.txtPriceBP);

        txtProductName.setText(mProductList.get(position).getProductName());
        txtPrice.setText("Price: " + String.valueOf(mProductList.get(position).getPrice()) + " RON");

        v.setTag(mProductList.get(position).getProductId());

        return v;
    }
}
