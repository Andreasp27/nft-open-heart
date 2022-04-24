package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_page);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.ownedItem);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);

        trendArrayList = new ArrayList<>();
        getData();

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
                            Trend ob1 = new Trend(item.getNama_item(), Float.toString(item.getHarga()), "Bored APE",base + item.getImage_path());
                            trendArrayList.add(ob1);
                        }
                        no++;
                    }
                    recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getApplicationContext()));
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