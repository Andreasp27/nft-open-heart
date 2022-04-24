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

public class CollectionPreviewItem extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Collected> collectedArrayList;
    CardView cardCollected;
    ImageView back, wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_preview_item);

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
                Intent intent = new Intent(CollectionPreviewItem.this, wallet.class);
                startActivity(intent);
            }
        });

        //placeholder display empty recycler
        cardCollected = findViewById(R.id.emptyCard2);

        recyclerView = findViewById(R.id.recyclerCollected);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        collectedArrayList = new ArrayList<>();
//        addData();

        recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, CollectionPreviewItem.this));

        if (collectedArrayList.size() == 0) {
            cardCollected.setVisibility(View.VISIBLE);
        }

    }
//    void addData(){
//        Collected ob1 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob1);
//        Collected ob2 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob2);
//        Collected ob3 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob3);
//        Collected ob4 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob4);
//        Collected ob5 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob5);
//        Collected ob6 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob6);
//        Collected ob7 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob7);
//    }
}