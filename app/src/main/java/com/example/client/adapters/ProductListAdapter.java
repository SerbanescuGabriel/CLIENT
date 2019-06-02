package com.example.client.adapters;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        final TextView txtQuantity = v.findViewById(R.id.txtQuantity);
        Button btnPlus, btnMinus;
        btnPlus = v.findViewById(R.id.btnPlusQtty);
        btnMinus = v.findViewById(R.id.btnMinusQtty);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(txtQuantity.getText().toString());
                value++;
                txtQuantity.setText(Integer.toString(value));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(txtQuantity.getText().toString());
                value--;
                if(value < 1) return;
                txtQuantity.setText(Integer.toString(value));
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
}
