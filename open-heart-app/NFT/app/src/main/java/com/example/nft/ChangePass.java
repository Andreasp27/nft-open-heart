package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePass extends AppCompatActivity {

    private TextInputLayout editLastPass, editNewPass, editConfPass;
    private Button changeBtn;
    private ImageButton backBtn;
    private Session session;
    private String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

        getSupportActionBar().hide();

        editLastPass = findViewById(R.id.last_pass);
        editNewPass = findViewById(R.id.new_pass);
        editConfPass = findViewById(R.id.confirm_pass);
        backBtn = findViewById(R.id.btn_back_pass);
        changeBtn = findViewById(R.id.btn_change_pass);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChangePass.changePassRequest changePassRequest = new changePassRequest();
                changePassRequest.setLast_password(editLastPass.getEditText().getText().toString());
                changePassRequest.setPassword(editNewPass.getEditText().getText().toString());
                changePassRequest.setConfirm_password(editConfPass.getEditText().getText().toString());

                Call<MessageResponse> messageResponseCall = ApiClient.getUserService().updatePass("Bearer "+ access_token, changePassRequest);
                messageResponseCall.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        String message = response.body().getMessage();
                        if (response.isSuccessful()){
                            if (message.equals("success")){
                                Toast.makeText(getApplicationContext(), "Update Password Success", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Update Password Failed" + message, Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Update Password Failed" + message, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Update Password Failed\n" + t , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public class changePassRequest{
        private String last_password;
        private String password;
        private String confirm_password;

        public String getLast_password() {
            return last_password;
        }

        public void setLast_password(String last_password) {
            this.last_password = last_password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }
    }
}