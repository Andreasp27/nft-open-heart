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

public class Send extends AppCompatActivity {

    private Session session;
    private String access_token;
    private Button sendBtn;
    private TextInputLayout userNum, nominal, pass;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        getSupportActionBar().hide();

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

        //get view
        userNum = findViewById(R.id.user_send);
        nominal = findViewById(R.id.nominal_send);
        pass = findViewById(R.id.pass_send);
        sendBtn = findViewById(R.id.btn_send);
        back = findViewById(R.id.btn_back_send);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userNum.getEditText().getText())){
                    userNum.getEditText().setError("User Number Field required");
                }else if (TextUtils.isEmpty(nominal.getEditText().getText())){
                    nominal.getEditText().setError("Nominal Field required");
                }else if (TextUtils.isEmpty(pass.getEditText().getText())){
                    pass.getEditText().setError("Password Field required");
                }else{

                    SendRequest sendRequest = new SendRequest();
                    sendRequest.setNomor_user(userNum.getEditText().getText().toString());
                    sendRequest.setSaldo(Float.parseFloat(nominal.getEditText().getText().toString()));
                    sendRequest.setPassword(pass.getEditText().getText().toString());

                    Call<MessageResponse> messageResponseCall = ApiClient.getUserService().send(
                            "Bearer "+ access_token, sendRequest);
                    messageResponseCall.enqueue(new Callback<MessageResponse>() {
                        @Override

                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            String message = response.body().getMessage();
                            if (response.isSuccessful()){
                                if (message.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Send Success", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(getApplicationContext(), wallet.class);
//                                    startActivity(intent);
                                    refreshActivity();
                                }else if (message.equals("failed_1")){
                                    Toast.makeText(getApplicationContext(), "Not enough balance", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Send Failed", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Send Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Send Failed: " + t, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public class SendRequest{
        private String nomor_user;
        private float saldo;
        private String password;

        public String getNomor_user() {
            return nomor_user;
        }

        public void setNomor_user(String nomor_user) {
            this.nomor_user = nomor_user;
        }

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