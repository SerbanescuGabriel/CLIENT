package com.example.client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.client.R;
import com.example.client.entitymodels.user.User;
import com.example.client.webservices.IUserWebservice;
import com.example.client.webservices.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etUsername, etPassword, etEmail;
    IUserWebservice userWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister=findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        etEmail = findViewById(R.id.etRegEmail);

        userWebservice = RetrofitSingleton.getInstance().create(IUserWebservice.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWebservice.register(
                        etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        etEmail.getText().toString()
                ).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = new User();
                        if(response.isSuccessful()){
                            user = response.body();
                        }

                        if(user.getUserId() > 0){
                            Intent intent=new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

    }
}
