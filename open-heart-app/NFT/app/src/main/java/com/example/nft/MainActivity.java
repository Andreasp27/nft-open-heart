package com.example.nft;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.LoginRequest;
import com.example.nft.api.LoginResponse;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    TextInputLayout email, password;
    Button btnLogin;
    TextView dont, click;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        getSupportActionBar().hide();

        logo = findViewById(R.id.logo);


        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        dont = findViewById(R.id.donthave);
        click = findViewById(R.id.clickhere);

        btnLogin = findViewById(R.id.login);

        logo.animate().translationY(-600).setDuration(1000).setStartDelay(1000);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);

        email.startAnimation(animFadeIn);
        password.startAnimation(animFadeIn);
        btnLogin.startAnimation(animFadeIn);
        dont.startAnimation(animFadeIn);
        click.startAnimation(animFadeIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Editable Email = email.getEditText().getText();
                Editable pass = password.getEditText().getText();

                if(TextUtils.isEmpty(Email)){
                    email.getEditText().setError("Email is Empty");

                } else if (TextUtils.isEmpty(pass)){
                    password.getEditText().setError("Password is Empty");
                } else {
                    login();
                }

            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);


            }
        });

    }
    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {
                    finish();
                }
            });
    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getEditText().getText().toString());
        loginRequest.setPassword(password.getEditText().getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                    LoginResponse access_token = response.body();

                    Toast.makeText(MainActivity.this, "Login success" , Toast.LENGTH_LONG).show();
                    session = new Session(MainActivity.this);
                    session.setAccessToken(access_token.getAccess_token());
                    session.setId(Integer.toString(access_token.getId()));
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                    intent.putExtra("key", access_token.getAccess_token());
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }

}