package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionPreviewItem extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Collected> collectedArrayList;
    CardView cardCollected;
    ImageView back, wallet;

    private String access_token, base;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_preview_item);

        getSupportActionBar().hide();

        //get access token
        session = new Session(getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

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
        getDataCollection();

//        recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, CollectionPreviewItem.this));


    }
    public void getDataCollection(){
        Call<Collection.MyCollectionResponse> myCollection = ApiClient.getUserService().getAllMyCollection("Bearer "+ access_token);
        myCollection.enqueue(new Callback<Collection.MyCollectionResponse>() {
            @Override
            public void onResponse(Call<Collection.MyCollectionResponse> call, Response<Collection.MyCollectionResponse> response) {
                if (response.isSuccessful()){
                    ArrayList<Market.CollectionResponse> dataCollected = response.body().getCollected();


                    for (Market.CollectionResponse item : dataCollected){

                        Collected ob1 = new Collected(base + item.getImage_path(), item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), item.getId());
                        collectedArrayList.add(ob1);

                    }


                    recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, CollectionPreviewItem.this));

                    if (collectedArrayList.size() == 0) {
                        cardCollected.setVisibility(View.VISIBLE);
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Collection.MyCollectionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data Failed: " + t , Toast.LENGTH_LONG).show();
            }
        });
    }
}