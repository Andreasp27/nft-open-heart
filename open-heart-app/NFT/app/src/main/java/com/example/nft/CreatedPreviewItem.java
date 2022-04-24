package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class CreatedPreviewItem extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Created> createdArrayList;
    CardView cardcreated;
    ImageView back, wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_preview_item);

        getSupportActionBar().hide();

        //Action Bar Function
        back = findViewById(R.id.btn_back_add_coll);
        wallet = findViewById(R.id.wallet_add_coll);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatedPreviewItem.this, wallet.class);
                startActivity(intent);
            }
        });

        //placeholder display empty recycler
        cardcreated = findViewById(R.id.emptyCard2);

        recyclerView = findViewById(R.id.recyclerCreated);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        createdArrayList = new ArrayList<>();
        addData2();

        recyclerView.setAdapter(new MyAdapterCreated(createdArrayList, CreatedPreviewItem.this));

        if (createdArrayList.size() == 0) {
            cardcreated.setVisibility(View.VISIBLE);
        }

    }

    void addData2() {
        Created ob1 = new Created("3D Cinema Human", "25 SKS", "Bored Ape", R.drawable.orang);
        createdArrayList.add(ob1);
    }

}