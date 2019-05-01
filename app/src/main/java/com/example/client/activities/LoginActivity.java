package com.example.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.client.R;
import com.example.client.webservices.userWebservice.UserWebservice;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin,btnRegister;
    private EditText etUsername, etPassword;
    private UserWebservice userWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        userWebservice = new UserWebservice();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, DashboardActivity.class);
                int userId = userWebservice.Login(etUsername.getText().toString(), etPassword.getText().toString());
                Log.i("userId", Integer.toString(userId));
                intent.putExtra(DashboardActivity.UID_KEY, userId);
                startActivity(intent);
            }
        });

    }
}
