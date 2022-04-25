package com.example.nft;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPreview extends AppCompatActivity {

    ImageView expand, back, itemImage, wallet;
    TextView desc, itemName, itemPrice, itemDesc, itemOwner, itemCreator;
    TextInputLayout bid;
    Button bidButton;
    RecyclerView recyclerView;
    ArrayList<Provenance> provenanceArrayList;
    MyAdapterProvenance myAdapterProvenance;
    private String access_token, base;
    private Session session;
    int id, idUser;

    LinearLayout layout_expand;
    MaterialCardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        getSupportActionBar().hide();

        //get id
        Intent intent = getIntent();
        id = (int) intent.getExtras().getSerializable("id");
//        Toast.makeText(getApplicationContext(), "id: " + id, Toast.LENGTH_LONG).show();

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();
        base = session.getBase();
        idUser = Integer.parseInt(session.getId());

        desc = findViewById(R.id.desc);
        expand = findViewById(R.id.expand);
        back = findViewById(R.id.btn_back_add_coll);
        itemName = findViewById(R.id.itemname);
        itemPrice = findViewById(R.id.price);
        itemDesc = findViewById(R.id.desc);
        itemOwner = findViewById(R.id.owner);
        itemCreator = findViewById(R.id.creator);
        bid = findViewById(R.id.bid);
        itemImage = findViewById(R.id.collection_image);
        wallet = findViewById(R.id.wallet_item);
        bidButton = findViewById(R.id.bidBtn);

        layout_expand = findViewById(R.id.layoutExpand);
        cardView = findViewById(R.id.cardExpand);

        getDataCollection();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemPreview.this, wallet.class);
                startActivity(intent);
            }
        });
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeBid();
            }
        });

        recyclerView = findViewById(R.id.recyclerProvenance);
        provenanceArrayList = new ArrayList<>();

        myAdapterProvenance = new MyAdapterProvenance(provenanceArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);




    }


    public void getDataCollection(){
        IdRequest idRequest = new IdRequest();
        idRequest.setId(id);
        Call<Market.CollectionResponse> collectionResponseCall = ApiClient.getUserService().getCollection("Bearer "+ access_token, idRequest);
        collectionResponseCall.enqueue(new Callback<Market.CollectionResponse>() {
            @Override
            public void onResponse(Call<Market.CollectionResponse> call, Response<Market.CollectionResponse> response) {
                if (response.isSuccessful()){

                    if(response.body().getUser().getId() != idUser){
                        bid.setVisibility(View.VISIBLE);
                        bidButton.setVisibility(View.VISIBLE);
                    }



                    expand.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View view) {

                            AutoTransition autoTransition = new AutoTransition();
                            autoTransition.setDuration(10);

                            AutoTransition autoTransition1 = new AutoTransition();
                            autoTransition1.setDuration(200);

                            if (layout_expand.getVisibility() == View.VISIBLE){
                                TransitionManager.beginDelayedTransition(cardView, autoTransition);
                                layout_expand.setVisibility(View.GONE);

                                RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rotate.setDuration(200);
                                rotate.setInterpolator(new LinearInterpolator());

                                expand.startAnimation(rotate);
                                rotate.setFillAfter(true);
                                //expand.setRotation(expand.getRotation() + 180);
                            }else {
                                TransitionManager.beginDelayedTransition(cardView, autoTransition1);
                                layout_expand.setVisibility(View.VISIBLE);
                                //expand.setRotation(expand.getRotation() + 180);

                                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rotate.setDuration(200);
                                rotate.setInterpolator(new LinearInterpolator());

                                expand.startAnimation(rotate);
                                rotate.setFillAfter(true);
                            }
                        }
                    });

                    //set view
                    itemName.setText(response.body().getNama_item());
                    itemPrice.setText(Float.toString(response.body().getHarga()));
                    itemDesc.setText(response.body().getDeskripsi());
                    itemOwner.setText(response.body().getUser().getName());
                    itemCreator.setText(response.body().getPembuat());
                    Picasso.get().load(base + response.body().getImage_path()).into(itemImage);

                    for (Market.BidderList itemBid : response.body().getDaftarbid()){
                        if(itemBid.getUser().getId() == idUser){
                            bid.getEditText().setText(Float.toString(itemBid.getHarga_bid()));
                        }
                    }

                    for(ItemPreview.History item : response.body().getHistory()){
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreated_at());
                        Provenance ob1 = new Provenance(item.getNama(), item.getAksi(), Float.toString(item.getHarga()) + " SKS", date );
                        provenanceArrayList.add(ob1);
                    }

                    recyclerView.setAdapter(new MyAdapterProvenance(provenanceArrayList, getApplicationContext()));


                }else{
                    Toast.makeText(getApplicationContext(), "fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Market.CollectionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fetch data failed" + t, Toast.LENGTH_LONG).show();


            }
        });
    }

    public void placeBid(){
        BidRequest bidRequest = new BidRequest();
        bidRequest.setJumlah_bid(Float.parseFloat(bid.getEditText().getText().toString()));
        bidRequest.setId_koleksi(id);

        Call<MessageResponse> bidRequestCall = ApiClient.getUserService().placeBid("Bearer "+ access_token, bidRequest);
        bidRequestCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("success")){
                        Toast.makeText(getApplicationContext(), "Place bid Success", Toast.LENGTH_LONG).show();
                        refreshActivity();
                    }else{
                        Toast.makeText(getApplicationContext(), "Place bid Failed", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Place bid failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Place bid failed: " + t, Toast.LENGTH_LONG).show();

            }
        });
    }

    public static class IdRequest{

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class BidRequest{
        private float jumlah_bid;
        private int id_koleksi;

        public float getJumlah_bid() {
            return jumlah_bid;
        }

        public void setJumlah_bid(float jumlah_bid) {
            this.jumlah_bid = jumlah_bid;
        }

        public int getId_koleksi() {
            return id_koleksi;
        }

        public void setId_koleksi(int id_koleksi) {
            this.id_koleksi = id_koleksi;
        }
    }

    public class History{
        private String nama;
        private String aksi;
        private float harga;
        private Date created_at;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getAksi() {
            return aksi;
        }

        public void setAksi(String aksi) {
            this.aksi = aksi;
        }

        public float getHarga() {
            return harga;
        }

        public void setHarga(float harga) {
            this.harga = harga;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }
    }

    public void refreshActivity() {
        Intent i = new Intent(this, Market.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}