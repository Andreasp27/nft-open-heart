package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    TextInputLayout email, password;
    Button btnLogin;
    TextView dont, click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }, 2000);



    }
}