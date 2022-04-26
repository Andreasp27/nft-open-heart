package com.example.nft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPreviewCollection extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Bidder> bidderArrayList;
    MyAdapterBidder myAdapterBidder;
    ImageView back, itemImage, wallet;
    TextView itemName, itemPrice, itemDesc, itemOwner, itemCreator;
    private String access_token, base;
    private Session session;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview_collection);

        getSupportActionBar().hide();

        //get id
        Intent intent = getIntent();
        id = (int) intent.getExtras().getSerializable("id");

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();
        base = session.getBase();

        //get view
        back = findViewById(R.id.btn_back_add_coll);
        itemName = findViewById(R.id.itemCollName);
        itemPrice = findViewById(R.id.collPrice);
        itemDesc = findViewById(R.id.collDesc);
        itemOwner = findViewById(R.id.collOwner);
        itemCreator = findViewById(R.id.collCreator);
        itemImage = findViewById(R.id.collection_img);
        wallet = findViewById(R.id.wallet_item_coll);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemPreviewCollection.this, wallet.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerItemPreviewCollection);
        bidderArrayList = new ArrayList<>();

        myAdapterBidder = new MyAdapterBidder(bidderArrayList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);

        getDataCollection();






    }

    public void getDataCollection(){
        ItemPreview.IdRequest idRequest = new ItemPreview.IdRequest();
        idRequest.setId(id);
        Call<Market.CollectionResponse> collectionResponseCall = ApiClient.getUserService().getCollection("Bearer "+ access_token, idRequest);
        collectionResponseCall.enqueue(new Callback<Market.CollectionResponse>() {
            @Override
            public void onResponse(Call<Market.CollectionResponse> call, Response<Market.CollectionResponse> response) {
                if (response.isSuccessful()){
                    itemName.setText(response.body().getNama_item());
                    itemPrice.setText(Float.toString(response.body().getHarga()));
                    itemDesc.setText(response.body().getDeskripsi());
                    itemCreator.setText(response.body().getPembuat());
                    itemOwner.setText(response.body().getUser().getName());
                    Picasso.get().load(base + response.body().getImage_path()).into(itemImage);

                    for (Market.BidderList item : response.body().getDaftarbid()){
                        Bidder obj = new Bidder(item.getUser().getName(), Float.toString(item.getHarga_bid()),item.getNomor_user(), access_token,id);
                        bidderArrayList.add(obj);
                    }

                    recyclerView.setAdapter(new MyAdapterBidder(bidderArrayList, getApplicationContext()));


                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Market.CollectionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class AcceptBidRequest{
        private String nomor_user;
        private int id_koleksi;

        public String getNomor_user() {
            return nomor_user;
        }

        public void setNomor_user(String nomor_user) {
            this.nomor_user = nomor_user;
        }

        public int getId_koleksi() {
            return id_koleksi;
        }

        public void setId_koleksi(int id_koleksi) {
            this.id_koleksi = id_koleksi;
        }
    }

    public void refreshActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}