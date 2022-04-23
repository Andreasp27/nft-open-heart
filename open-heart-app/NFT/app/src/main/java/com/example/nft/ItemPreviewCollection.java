package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ItemPreviewCollection extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Bidder> bidderArrayList;
    MyAdapterBidder myAdapterBidder;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview_collection);

        getSupportActionBar().hide();

        back = findViewById(R.id.btn_back_add_coll);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerItemPreviewCollection);
        bidderArrayList = new ArrayList<>();

        myAdapterBidder = new MyAdapterBidder(bidderArrayList, this);
        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapterBidder(bidderArrayList, getApplicationContext()));

    }
    void addData() {
        Bidder ob1 = new Bidder("Bambank123", "125");
        bidderArrayList.add(ob1);
        Bidder ob2 = new Bidder("Bambank123", "125");
        bidderArrayList.add(ob2);
        Bidder ob3 = new Bidder("Bambank123", "125");
        bidderArrayList.add(ob3);
        Bidder ob4 = new Bidder("Bambank123", "125");
        bidderArrayList.add(ob4);
    }
}