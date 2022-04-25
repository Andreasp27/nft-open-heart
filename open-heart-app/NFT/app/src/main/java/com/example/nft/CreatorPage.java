package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

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
    ImageView wallet, back, banner, profile;
    LikeButton like;
    TextView name, desc;
    private int id, idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_page);

        getSupportActionBar().hide();

        //get id
        Intent intent = getIntent();
        id = (int) intent.getExtras().getSerializable("id");

        //get access token
        session = new Session(getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();
        idUser = Integer.parseInt(session.getId());

        wallet = findViewById(R.id.wallet_add_coll);
        back = findViewById(R.id.btn_back_add_coll);
        like = findViewById(R.id.star_button);
        banner = findViewById(R.id.bannerCreator);
        profile = findViewById(R.id.profile_img_creator);
        name = findViewById(R.id.creatorName);
        desc = findViewById(R.id.bioCreator);

        getCreator();


        like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(getApplicationContext(),"liked", Toast.LENGTH_LONG).show();
                likeCreator();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                like.setLiked(false);
                Toast.makeText(getApplicationContext(),"unliked", Toast.LENGTH_LONG).show();
                likeCreator();

            }
        });

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
        trendArrayList = new ArrayList<>();

    }

    void getCreator(){

        ItemPreview.IdRequest idRequest = new ItemPreview.IdRequest();
        idRequest.setId(id);

        Call<EditProfile.ProfileRR> profileRRCall = ApiClient.getUserService().getCreator("Bearer "+ access_token, idRequest);
        profileRRCall.enqueue(new Callback<EditProfile.ProfileRR>() {
            @Override
            public void onResponse(Call<EditProfile.ProfileRR> call, Response<EditProfile.ProfileRR> response) {
                if (response.isSuccessful()){
                    name.setText(response.body().getName());
                    desc.setText(response.body().getBio());
                    Picasso.get().load(base + response.body().getBanner_path()).into(banner);
                    Picasso.get().load(base + response.body().getGambar_path()).into(profile);

                    //set like
                    for (EditProfile.Like itemt : response.body().getLike()){
                        if (itemt.getLiked_by() == idUser){
                            like.setLiked(true);
                        }
                    }

                    //set list
                    for (Market.CollectionResponse item : response.body().getKoleksi()){
                        Trend ob1 = new Trend(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(),base + item.getImage_path(), item.getId());
                        trendArrayList.add(ob1);
                    }
                    recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, CreatorPage.this));


                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EditProfile.ProfileRR> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    void likeCreator(){
        ItemPreview.IdRequest idRequest = new ItemPreview.IdRequest();
        idRequest.setId(id);

        Call<MessageResponse> call = ApiClient.getUserService().like("Bearer "+ access_token, idRequest);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


}