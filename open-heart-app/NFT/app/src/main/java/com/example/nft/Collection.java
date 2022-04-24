package com.example.nft;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Collection extends Fragment {

    RecyclerView recyclerView, recyclerCreated;
    ArrayList<Collected> collectedArrayList;
    ArrayList<Created> createdArrayList;
    CardView cardCreated, cardCollected;
    FloatingActionButton addCollection;
    TextView See1, See2;

    private String access_token, base;
    private Session session;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        //get access token
        session = new Session(getActivity().getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

        cardCreated = view.findViewById(R.id.emptyCard);
        cardCollected = view.findViewById(R.id.emptyCard2);
        addCollection = view.findViewById(R.id.addCollection);


        //see all button
        See1 = view.findViewById(R.id.seeAll1);
        See2 = view.findViewById(R.id.seeAll2);

        See1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CollectionPreviewItem.class);
                startActivity(intent);
            }
        });

        See2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreatedPreviewItem.class);
                startActivity(intent);
            }
        });


        //Collection Section

        recyclerView = view.findViewById(R.id.recyclerCollected);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        collectedArrayList = new ArrayList<>();

        //created section
        recyclerCreated = view.findViewById(R.id.recyclerCreated);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerCreated.setLayoutManager(gridLayoutManager1);

        createdArrayList = new ArrayList<>();

        getDataCollection();


        //Check collected length
        if (collectedArrayList.size() == 0) {
            cardCollected.setVisibility(View.VISIBLE);
        }

        addCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddCollection.class);
                startActivity(intent);
            }
        });

        //Check created length
        if (createdArrayList.size() == 0) {
            cardCreated.setVisibility(View.VISIBLE);
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    public void getDataCollection(){
        Call<MyCollectionResponse> myCollection = ApiClient.getUserService().getAllMyCollection("Bearer "+ access_token);
        myCollection.enqueue(new Callback<MyCollectionResponse>() {
            @Override
            public void onResponse(Call<MyCollectionResponse> call, Response<MyCollectionResponse> response) {
                if (response.isSuccessful()){
                    ArrayList<Market.CollectionResponse> dataCreated = response.body().getCreated();
                    ArrayList<Market.CollectionResponse> dataCollected = response.body().getCollected();
                    int totalCollected = 1;
                    int totalCreated = 1;

                    for (Market.CollectionResponse item : dataCollected){
                        if(totalCollected <= 2){
                            Collected ob1 = new Collected(base + item.getImage_path(), item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), item.getId());
                            collectedArrayList.add(ob1);
                            totalCollected++;
                        }
                    }

                    for (Market.CollectionResponse item : dataCreated){
                        if(totalCreated <= 2){
                        Created ob2 = new Created(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), base + item.getImage_path(), item.getId());
                        createdArrayList.add(ob2);
                        totalCreated++;
                        }
                    }

                    recyclerCreated.setAdapter(new MyAdapterCreated(createdArrayList, getContext()));
                    recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, getContext()));

                    if (collectedArrayList.size() == 0) {
                        cardCollected.setVisibility(View.VISIBLE);
                    }

                    if (createdArrayList.size() == 0) {
                        cardCreated.setVisibility(View.VISIBLE);
                    }

                }else{
                    Toast.makeText(getContext(), "Fetch data Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyCollectionResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Fetch data Failed: " + t , Toast.LENGTH_LONG).show();
            }
        });
    }

    public class MyCollectionResponse{
        private ArrayList<Market.CollectionResponse> created;
        private ArrayList<Market.CollectionResponse> collected;

        public ArrayList<Market.CollectionResponse> getCollected() {
            return collected;
        }

        public void setCollected(ArrayList<Market.CollectionResponse> collected) {
            this.collected = collected;
        }

        public ArrayList<Market.CollectionResponse> getCreated() {
            return created;
        }

        public void setCreated(ArrayList<Market.CollectionResponse> created) {
            this.created = created;
        }
    }
}