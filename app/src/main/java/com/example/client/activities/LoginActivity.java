package com.example.client.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.user.User;
import com.example.client.webservices.IUserWebservice;
import com.example.client.webservices.Messages;
import com.example.client.webservices.RetrofitSingleton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText etUsername, etPassword;
    private IUserWebservice userWebservice;
    private int id, idSP;
    private CheckBox cbKeepLoggedIn;
    SharedPreferences sp;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbKeepLoggedIn = findViewById(R.id.cbKeepLoggedIn);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");

        userWebservice = RetrofitSingleton.getInstance().create(IUserWebservice.class);

        sp = getSharedPreferences("userId", MODE_PRIVATE);
        idSP = sp.getInt("userId", MODE_PRIVATE);

        if (idSP > 0) {
            goDirectlyToDashboard(idSP);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                userWebservice.login(etUsername.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User user = new User();

                        if (response.isSuccessful()) {
                            user = response.body();
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);

                                SharedPreferences.Editor editor = sp.edit();
                                id = (int) user.getUserId();
                                editor.putInt("userId", id);
                                editor.commit();
                                progressDialog.cancel();


                        } else {
                            if (response.code() == 400) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.errorBody().string());
                                    String content = jsonObject.getString("Message");

                                    if (content.equals(Messages.Error_InvalidCredentials)) {
                                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                        progressDialog.cancel();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("LoginError", t.getMessage());
                    }
                });
            }
        });
    }

    private void goDirectlyToDashboard(int id) {

        userWebservice.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = new User();
                if (response.isSuccessful()) {
                    user = response.body();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                   // intent.putExtra("user", u);

                    Bundle bundle=new Bundle();
                    //intent.putExtra("user", user);
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else {
                    //bad response
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("StayLoggedIn", t.getMessage());
            }
        });
    }
}
