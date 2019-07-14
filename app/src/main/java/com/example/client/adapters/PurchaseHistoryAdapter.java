package com.example.client.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.entitymodels.cart.Cart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PurchaseHistoryAdapter extends BaseAdapter {

    private List<Cart> mCartList;
    private Context mContext;

    public PurchaseHistoryAdapter(Context mContext, List<Cart> mCartList){
        this.mCartList = mCartList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return  mCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(mContext, R.layout.item_purchase_history,null);
        TextView txtProductName=v.findViewById(R.id.txtDateOfPurchase);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(mCartList.get(position).getPurchaseDate());

        txtProductName.setText("Your purchase from: " + strDate);

        return v;
    }

}
