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

public class CreatedPreviewItem extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Created> createdArrayList;
    CardView cardcreated;
    ImageView back, wallet;

    private String access_token, base;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_preview_item);

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
        getDataCollection();
//        addData2();

//        recyclerView.setAdapter(new MyAdapterCreated(createdArrayList, CreatedPreviewItem.this));


    }

    public void getDataCollection(){
        Call<Collection.MyCollectionResponse> myCollection = ApiClient.getUserService().getAllMyCollection("Bearer "+ access_token);
        myCollection.enqueue(new Callback<Collection.MyCollectionResponse>() {
            @Override
            public void onResponse(Call<Collection.MyCollectionResponse> call, Response<Collection.MyCollectionResponse> response) {
                if (response.isSuccessful()){
                    ArrayList<Market.CollectionResponse> dataCreated = response.body().getCreated();

                    for (Market.CollectionResponse item : dataCreated){
                        Created ob2 = new Created(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), base + item.getImage_path(), item.getId());
                        createdArrayList.add(ob2);
                    }


                    recyclerView.setAdapter(new MyAdapterCreated(createdArrayList, CreatedPreviewItem.this));

                    if (createdArrayList.size() == 0) {
                        cardcreated.setVisibility(View.VISIBLE);
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