package com.example.client.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.adapters.ProductListAdapter;
import com.example.client.entitymodels.product.Product;
import com.example.client.webservices.ICartWebservice;
import com.example.client.webservices.RetrofitSingleton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private TextView txtCart;
    private ICartWebservice cartWebservice;
    private ListView listViewProducts;
    private ListAdapter adapter;
    private Button btnGenerateQRCode;
    SharedPreferences sp;
    private Dialog dialogQrCode;
    private ImageView imgViewQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        cartWebservice= RetrofitSingleton.getInstance().create(ICartWebservice.class);
        getCartItems();

        btnGenerateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });
    }

    private void generateQRCode() {

        sp=getSharedPreferences("userId",MODE_PRIVATE);
        int userId=sp.getInt("userId",MODE_PRIVATE);
        cartWebservice.getCartId(userId).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                long cartId;
                if(response.isSuccessful()){
                    cartId=response.body();

                    String code=Long.toString(cartId);

                    AlertDialog.Builder imgDialog = new AlertDialog.Builder(CartActivity.this);
                    imgDialog.setTitle("Your cart!");
                    ImageView imgView = new ImageView(CartActivity.this);
                    MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix=multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                        imgView.setImageBitmap(bitmap);
                        imgDialog.setView(imgView);
                        imgDialog.show();

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
            }
        });
    }

    private void getCartItems() {
        sp=getSharedPreferences("userId", MODE_PRIVATE);
        int userId=sp.getInt("userId",MODE_PRIVATE);
        cartWebservice.getCartProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> cart=new ArrayList<>();

                if(response.isSuccessful()){
                    cart=response.body();
                    adapter=new ProductListAdapter(getApplicationContext(),cart);
                    listViewProducts.setAdapter(adapter);
                    //todo
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                    //todo
            }
        });
    }

    private void setControl() {

        listViewProducts=findViewById(R.id.listview_product);
        btnGenerateQRCode=findViewById(R.id.btnGenerateQRCode);
    }
}
