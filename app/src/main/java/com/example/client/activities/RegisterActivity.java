package com.example.client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.user.User;
import com.example.client.entitymodels.user.UserDetails;
import com.example.client.webservices.IUserWebservice;
import com.example.client.webservices.Messages;
import com.example.client.webservices.RetrofitSingleton;

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
    private EditText etUsername, etPassword, etEmail,etFirstName,etLastName,etAge;
    private RadioGroup rgSex;
    private RadioButton rbMale, rbFemale;
    IUserWebservice userWebservice;
    private  Boolean userGender;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        populateControls();
        sp = getSharedPreferences("userId", MODE_PRIVATE);
        userWebservice = RetrofitSingleton.getInstance().create(IUserWebservice.class);

        //todo-> validation on etUsername

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String email=etEmail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if(!(email.matches(emailPattern))){
                        etEmail.setError("invalid email address");
                    }
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                   if(!(isValidPassword(etPassword.getText().toString().trim()))){
                       etPassword.setError("invalid password. Please be sure your password has One capital letter, one number" +
                              " and one symbol (@,$,%,&,#,)");
                   }
                }
            }
        });


        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!(isValidName(etFirstName.getText().toString().trim()))){
                        etFirstName.setError("please enter only characters");
                    }
                }
            }
        });

        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!(isValidName(etLastName.getText().toString().trim()))){
                        etLastName.setError("please enter only characters");
                    }
                }
            }
        });

        etEmail.addTextChangedListener(registerTextWatcher);
        etUsername.addTextChangedListener(registerTextWatcher);
        etEmail.addTextChangedListener(registerTextWatcher);
        etFirstName.addTextChangedListener(registerTextWatcher);
        etLastName.addTextChangedListener(registerTextWatcher);
        etAge.addTextChangedListener(registerTextWatcher);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rgSex.getCheckedRadioButtonId()!=-1){
                    btnRegister.setEnabled(false);
                }else{
                    btnRegister.setEnabled(true);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(rgSex.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "please choose your gender", Toast.LENGTH_SHORT).show();
               }
               else {

                   if (rbMale.isChecked()) {
                       userGender = true;
                   }
                   else userGender=false;
               }
                userWebservice.register(
                        etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        etEmail.getText().toString(),
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()),
                        userGender
                ).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = new User();
                        if(response.isSuccessful()){
                            user = response.body();
                            saveUserId((int)user.getUserId());
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

    private  TextWatcher registerTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String emailInput=etEmail.getText().toString().trim();
            String usernameInput=etUsername.getText().toString().trim();
            String passwordInput=etPassword.getText().toString().trim();
            String firstNameInput=etFirstName.getText().toString().trim();
            String lastNameInput=etLastName.getText().toString().trim();
            int ageInput = 0;
            String testValue = etAge.getText().toString();
            int age=0;

            if(!(etAge.getText().toString().equals(""))) {
                //age=Integer.parseInt(etAge.getText().toString());
                btnRegister.setEnabled(!emailInput.isEmpty() && !usernameInput.isEmpty() && !passwordInput.isEmpty() &&
                        !firstNameInput.isEmpty() && !lastNameInput.isEmpty());
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean isValidName(String firstName) {

        Pattern pattern=Pattern.compile("^[a-zA-Z ]+$");
        Matcher matcher=pattern.matcher(firstName);
        Boolean isValid=matcher.matches();

        return isValid;
    }

    private void populateControls() {

        btnRegister=findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        etEmail = findViewById(R.id.etRegEmail);
        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etAge=findViewById(R.id.etAge);
        rgSex=findViewById(R.id.rgSex);
        rbMale=findViewById(R.id.rbMale);
        rbFemale=findViewById(R.id.rbFemale);


    }

    public boolean isValidPassword(String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern=Pattern.compile(PASSWORD_PATTERN);
        matcher=pattern.matcher(password);
        return matcher.matches();

    }

    public void saveUserId(int userId){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("userId", userId);
        editor.commit();
    }
}
