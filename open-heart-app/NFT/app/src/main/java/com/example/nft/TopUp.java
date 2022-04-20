package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class TopUp extends AppCompatActivity {

    private ImageView back;
    private Button topUp;
    private TextInputLayout nominal, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        getSupportActionBar().hide();

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

            }
        });

    }


}