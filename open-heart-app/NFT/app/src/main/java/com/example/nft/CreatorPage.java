package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class CreatorPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Trend> trendArrayList;
    MyAdapterTrend myAdapterTrend;
    private String access_token, base;
    private Session session;
    ImageView wallet, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_page);

        getSupportActionBar().hide();

        //get access token
        session = new Session(getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

        wallet = findViewById(R.id.wallet_add_coll);
        back = findViewById(R.id.btn_back_add_coll);

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatorPage.this, wallet.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.ownedItem);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);
        getData();
        trendArrayList = new ArrayList<>();


        //recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getApplicationContext()));

    }

    void getData(){
        Call<ArrayList<Market.CollectionResponse>> collectionResponseCall = ApiClient.getUserService().getAllTrending("Bearer "+ access_token);
        collectionResponseCall.enqueue(new Callback<ArrayList<Market.CollectionResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<Market.CollectionResponse>> call, Response<ArrayList<Market.CollectionResponse>> response) {
                if (response.isSuccessful()){
                    ArrayList<Market.CollectionResponse> data = response.body();
                    int no = 1;
                    for (Market.CollectionResponse item : data){
                        if (no <= 2){
                            Trend ob1 = new Trend(item.getNama_item(), Float.toString(item.getHarga()), "Bored APE",base + item.getImage_path(), item.getId());
                            trendArrayList.add(ob1);
                        }
                        no++;
                    }
                    recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, CreatorPage.this));
                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Market.CollectionResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}