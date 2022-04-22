package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddCollection extends AppCompatActivity {

    ImageView backBtn, wallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        getSupportActionBar().hide();

        backBtn = findViewById(R.id.btn_back_add_coll);
        wallet = findViewById(R.id.wallet_add_coll);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCollection.this, wallet.class);
                startActivity(intent);
            }
        });
    }
}