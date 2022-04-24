package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPreview extends AppCompatActivity {

    ImageView expand, back;
    TextView desc, itemName, itemPrice, itemDesc, itemOwner, itemCreator;
    ImageView itemImage;
    RecyclerView recyclerView;
    ArrayList<Provenance> provenanceArrayList;
    MyAdapterProvenance myAdapterProvenance;
    private String access_token, base;
    private Session session;
    int id;

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

        desc = findViewById(R.id.desc);
        expand = findViewById(R.id.expand);
        back = findViewById(R.id.btn_back_add_coll);
        itemName = findViewById(R.id.itemname);
        itemPrice = findViewById(R.id.price);
        itemDesc = findViewById(R.id.desc);
        itemOwner = findViewById(R.id.owner);
        itemCreator = findViewById(R.id.creator);
        itemImage = findViewById(R.id.collection_image);

        getDataCollection();

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
//        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);


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

    public void getDataCollection(){
        IdRequest idRequest = new IdRequest();
        idRequest.setId(id);
        Call<Market.CollectionResponse> collectionResponseCall = ApiClient.getUserService().getCollection("Bearer "+ access_token, idRequest);
        collectionResponseCall.enqueue(new Callback<Market.CollectionResponse>() {
            @Override
            public void onResponse(Call<Market.CollectionResponse> call, Response<Market.CollectionResponse> response) {
                if (response.isSuccessful()){

                    //set view
                    itemName.setText(response.body().getNama_item());
                    itemPrice.setText(Float.toString(response.body().getHarga()));
                    itemDesc.setText(response.body().getDeskripsi());
                    itemOwner.setText(response.body().getUser().getName());
                    itemCreator.setText(response.body().getPembuat());
                    Picasso.get().load(base + response.body().getImage_path()).into(itemImage);

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

    public  class IdRequest{
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;
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

}