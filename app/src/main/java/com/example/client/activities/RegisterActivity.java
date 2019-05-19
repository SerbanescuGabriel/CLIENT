package com.example.client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.user.User;
import com.example.client.webservices.IUserWebservice;
import com.example.client.webservices.Messages;
import com.example.client.webservices.RetrofitSingleton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String email=etEmail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if(email.matches(emailPattern)){
                        Toast.makeText(getApplicationContext(),"valid email address", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"invalid email address", Toast.LENGTH_LONG);
                    }
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                   if(isValidPassword(etPassword.getText().toString().trim())){
                       Toast.makeText(getApplicationContext(), "valid password", Toast.LENGTH_LONG).show();
                   }
                   else{
                       Toast.makeText(getApplicationContext(), "invalid password. Please be sure your password has One capital letter, one number" +
                              " and one symbol (@,$,%,&,#,)", Toast.LENGTH_LONG).show();
                   }
                }
            }
        });

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
                            Intent intent=new Intent(RegisterActivity.this, DashboardActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);

                        }else{
                           if(response.code()==400){
                               JSONObject jsonObject=null;

                               try {
                                   jsonObject=new JSONObject(response.errorBody().string());
                                   String content=jsonObject.getString("Message");

                                   if(content.equals(Messages.Error_UsernameIsTaken)) {
                                       Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                   }
                               } catch (IOException e) {
                                   e.printStackTrace();
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public boolean isValidPassword(String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern=Pattern.compile(PASSWORD_PATTERN);
        matcher=pattern.matcher(password);
        return matcher.matches();

    }
}
