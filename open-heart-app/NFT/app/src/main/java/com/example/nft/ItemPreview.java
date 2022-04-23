package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemPreview extends AppCompatActivity {

    ImageView expand, back;
    TextView desc;
    RecyclerView recyclerView;
    ArrayList<Provenance> provenanceArrayList;
    MyAdapterProvenance myAdapterProvenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        getSupportActionBar().hide();

        desc = findViewById(R.id.desc);
        expand = findViewById(R.id.expand);
        back = findViewById(R.id.btn_back_add_coll);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand.setRotation(expand.getRotation() + 180);
            }
        });

        recyclerView = findViewById(R.id.recyclerProvenance);
        provenanceArrayList = new ArrayList<>();

        myAdapterProvenance = new MyAdapterProvenance(provenanceArrayList, this);
        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapterProvenance(provenanceArrayList, getApplicationContext()));

    }
    void addData() {
        Provenance ob1 = new Provenance("Bambank123", "Purchased", "120 SKS", "2022-03 -17 12:03:45" );
        provenanceArrayList.add(ob1);
        Provenance ob2 = new Provenance("Bambank123", "Purchased", "120 SKS", "2022-03 -17 12:03:45" );
        provenanceArrayList.add(ob2);
        Provenance ob3 = new Provenance("Bambank123", "Purchased", "120 SKS", "2022-03 -17 12:03:45" );
        provenanceArrayList.add(ob3);
        Provenance ob4 = new Provenance("Bambank123", "Purchased", "120 SKS", "2022-03 -17 12:03:45" );
        provenanceArrayList.add(ob4);
    }

}