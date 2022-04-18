package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class wallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().hide();
    }
}