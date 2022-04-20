package com.example.nft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Trending extends Fragment {

    MyAdapterTopSales myAdapterTopSales;
    RecyclerView recyclerView;
    ArrayList<TopSales> topSales;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        recyclerView = view.findViewById(R.id.recyclerTrend);
        topSales = new ArrayList<>();

        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(layoutManager);

        myAdapterTopSales = new MyAdapterTopSales(topSales, getContext());
        recyclerView.setAdapter(new MyAdapterTopSales(topSales, getContext()));

        return  view;
    }

    void addData() {
        TopSales ob1 = new TopSales(R.drawable.naruto, "1", "Naruto Shipuden", "120,99", "46.5 %");
        topSales.add(ob1);
        TopSales ob2 = new TopSales(R.drawable.naruto, "2", "Naruto Shipuden", "120,99", "46.5 %");
        topSales.add(ob2);
        TopSales ob3 = new TopSales(R.drawable.naruto, "3", "Naruto Shipuden", "120,99", "46.5 %");
        topSales.add(ob3);
    }
}