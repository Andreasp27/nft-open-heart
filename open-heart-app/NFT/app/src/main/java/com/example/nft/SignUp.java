package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView logoSign;
    TextInputLayout emailSign, passwordSign, name, ConfirmPassSign;
    Button btnSign;
    TextView have, clickSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        logoSign = findViewById(R.id.logo);

        emailSign = findViewById(R.id.email);
        passwordSign = findViewById(R.id.password);
        ConfirmPassSign = findViewById(R.id.Cpassword);
        name = findViewById(R.id.name);

        have = findViewById(R.id.haveAcc);
        clickSign = findViewById(R.id.clickhere);

        btnSign = findViewById(R.id.signup);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeinsign);

        logoSign.startAnimation(animFadeIn);
        emailSign.startAnimation(animFadeIn);
        passwordSign.startAnimation(animFadeIn);
        ConfirmPassSign.startAnimation(animFadeIn);
        name.startAnimation(animFadeIn);
        btnSign.startAnimation(animFadeIn);
        have.startAnimation(animFadeIn);
        clickSign.startAnimation(animFadeIn);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable email = emailSign.getEditText().getText();
                Editable namesign = name.getEditText().getText();
                Editable Pass = passwordSign.getEditText().getText();
                Editable CPass = ConfirmPassSign.getEditText().getText();

                String firstPass = Pass.toString();
                String secPass = CPass.toString();

                if (TextUtils.isEmpty(email)){
                    emailSign.getEditText().setError("Email Cannot be Empty");
                } else if (TextUtils.isEmpty(namesign)){
                    name.getEditText().setError("Name Cannot be Empty");
                } else if (TextUtils.isEmpty(Pass)){
                    passwordSign.getEditText().setError("password cannot be Empty");
                } else if (TextUtils.isEmpty(CPass)) {
                    ConfirmPassSign.getEditText().setError("password cannot be Empty");
                } else if (!firstPass.equals(secPass)){
                    Toast.makeText(SignUp.this,"Passowrd and Confirm Password Does not Match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this,"Sign Up Success", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

    }
    public void login (View v) {
        finish();
    }
}