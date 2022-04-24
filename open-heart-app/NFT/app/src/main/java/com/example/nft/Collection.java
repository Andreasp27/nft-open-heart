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

        recyclerView = view.findViewById(R.id.recyclerCollected);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        collectedArrayList = new ArrayList<>();
        addData();
        recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, getContext()));

        //created section
        recyclerCreated = view.findViewById(R.id.recyclerCreated);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerCreated.setLayoutManager(gridLayoutManager1);

        createdArrayList = new ArrayList<>();
        addData2();
        recyclerCreated.setAdapter(new MyAdapterCreated(createdArrayList, getContext()));

        if (collectedArrayList.size() == 0) {
//            Toast.makeText(getContext(), "kososng", Toast.LENGTH_SHORT).show();
            cardCollected.setVisibility(View.VISIBLE);

        }

        if (createdArrayList.size() == 0) {
            cardCreated.setVisibility(View.VISIBLE);
        }

        addCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddCollection.class);
                startActivity(intent);
            }
        });

        return view;
    }

    void addData() {
        Collected ob1 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
        collectedArrayList.add(ob1);
//        Collected ob2 = new Collected(R.drawable.boredape, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob2);
    }

    void addData2() {
        Created ob1 = new Created("3D Cinema Human", "25 SKS", "Bored Ape", R.drawable.orang);
        createdArrayList.add(ob1);
    }

    public void getDataCollection(){
        Call<ArrayList<Market.CollectionResponse>> myCollection = ApiClient.getUserService().getAllMyCollection("Bearer "+ access_token);
        myCollection.enqueue(new Callback<ArrayList<Market.CollectionResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<Market.CollectionResponse>> call, Response<ArrayList<Market.CollectionResponse>> response) {
                if ()
            }

            @Override
            public void onFailure(Call<ArrayList<Market.CollectionResponse>> call, Throwable t) {

            }
        });
    }
}