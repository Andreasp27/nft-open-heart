package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.Toast;


import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUp extends AppCompatActivity {


    private ImageView back;

    private TextInputLayout nominal, pass;

    private Button topUp;
    private Session session;
    private String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        getSupportActionBar().hide();

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

        //get view
        back = findViewById(R.id.btn_back_topup);
        topUp = findViewById(R.id.btn_top_up);
        nominal = findViewById(R.id.nominal);
        pass = findViewById(R.id.pass_top_up);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(nominal.getEditText().getText())){
                    nominal.getEditText().setError("Nominal Transfer Field required");
                }else if(TextUtils.isEmpty(pass.getEditText().getText())){
                    pass.getEditText().setError("Password Field required");
                }else{

                    TopUpRequest topUpRequest = new TopUpRequest();
                    topUpRequest.setSaldo(Float.parseFloat(nominal.getEditText().getText().toString()));
                    topUpRequest.setPassword(pass.getEditText().getText().toString());

                    Call<MessageResponse> messageResponseCall = ApiClient.getUserService().topUp("Bearer "+ access_token, topUpRequest);
                    messageResponseCall.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            String message = response.body().getMessage();
                            if (response.isSuccessful()){
                                if (message.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Top Up Success", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(getApplicationContext(), wallet.class);
//                                    startActivity(intent);
                                    refreshActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Top Up Failed", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Top Up Failed", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Top Up failed" + t, Toast.LENGTH_LONG).show();

                        }
                    });

                }
            }
        });

    }

    public class TopUpRequest{
        private float saldo;
        private String password;

        public float getSaldo() {
            return saldo;
        }

        public void setSaldo(float saldo) {
            this.saldo = saldo;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public void refreshActivity() {
        Intent i = new Intent(this, wallet.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }

}