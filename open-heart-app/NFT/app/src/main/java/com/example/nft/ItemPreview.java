package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemPreview extends AppCompatActivity {

    ImageView expand;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        getSupportActionBar().hide();

        desc = findViewById(R.id.desc);
        expand = findViewById(R.id.expand);

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand.setRotation(expand.getRotation() + 180);
            }
        });

    }

}