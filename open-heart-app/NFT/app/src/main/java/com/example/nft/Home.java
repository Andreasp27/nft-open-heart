package com.example.nft;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private MyAdapterTrend adapterTrend;
    private ArrayList<Recom> recomArrayList;
    private ArrayList<Trend> trendArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Trend Creator
        recyclerView = view.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recomArrayList = new ArrayList<>();

        addData();
        recyclerView.setAdapter(new MyAdapter(recomArrayList, getContext()));

        //Trend item
        recyclerView = view.findViewById(R.id.recyclerTrend);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);
        trendArrayList = new ArrayList<>();

        addData2();
        recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getContext()));


        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.banner);
        ArrayList <SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.orang, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.naruto, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.akatzuki, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);



//        recyclerView = view.findViewById(R.id.recycler);
//        addData();
//
//        adapter = new MyAdapter(recomArrayList,getContext());
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    void addData() {
        Recom ob1 = new Recom("Akatzuki", "TOP 1 BINANCE ARTIST\n" +
                "▵Best and profitable NFT on Binance \n" +
                "https://www.instagram.com/nft.pride/\n" +
                "https://discord.gg/BV2gsuSz2A", R.drawable.akatzuki, R.drawable.orang);
        recomArrayList.add(ob1);
        Recom ob2 = new Recom("Bambank", "FEKIKI is the Secret Key to unlock more FEKIRA UNIVERSE benefits in the near futur..", R.drawable.akatzuki, R.drawable.naruto);
        recomArrayList.add(ob2);
    }

//    void addDatabanner() {
//        SlideModel ob1 = new SlideModel("https://public.nftstatic.com/static/nft/res/nft-cex/S3/1649823900545_6jn5ubar1ajr28zb2py05z7lvsprhcmh.png");
//        SlideModel.add(ob1);
//    }

    void addData2() {
        Trend ob1 = new Trend("Bored APE #1003", "25 SKS", "Bored APE", R.drawable.orang);
        trendArrayList.add(ob1);
        Trend ob2 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
        trendArrayList.add(ob2);
    }
    

}