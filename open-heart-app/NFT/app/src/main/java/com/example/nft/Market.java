package com.example.nft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Market extends Fragment {

    RecyclerView recyclerView;
    MyAdapterTrend myAdapterTrend;
    ArrayList<Trend> trendArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        recyclerView = view.findViewById(R.id.recyclerMarket);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);
        trendArrayList = new ArrayList<>();

        addData2();
        recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getContext()));

        return view;
    }

    void addData2() {
        Trend ob1 = new Trend("Bored APE #1003", "25 SKS", "Bored APE", R.drawable.orang);
        trendArrayList.add(ob1);
        Trend ob2 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob2);
        Trend ob3 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob3);
        Trend ob4 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob4);
        Trend ob5 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob5);
        Trend ob6 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob6);
        Trend ob7 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob7);
        Trend ob8 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob8);
    }
}