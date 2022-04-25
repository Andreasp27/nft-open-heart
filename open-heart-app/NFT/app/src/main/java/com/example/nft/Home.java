package com.example.nft;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {
    private RecyclerView recyclerView, recyclerTrend, recyclerBanner;
    private MyAdapter adapter;
    private MyAdapterTrend adapterTrend;
    private ArrayList<Recom> recomArrayList;
    private ArrayList<Trend> trendArrayList;
    private ArrayList<Banner> bannerArrayList;
    private SliderAdapter bannerAdapter;

    private String access_token, base;
    private Session session;
    private TextView more;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //get access token
        session = new Session(getActivity().getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

        more = view.findViewById(R.id.more_trending);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.popBackStack(R.id.home2, true);
                controller.navigate(R.id.trending);
            }
        });

        //Trend Creator
        recyclerView = view.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recomArrayList = new ArrayList<>();

        addData();
        recyclerView.setAdapter(new MyAdapter(recomArrayList, getContext()));


        //Trend item
        recyclerTrend = view.findViewById(R.id.recyclerTrend);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerTrend.setLayoutManager(gridLayoutManager2);
        trendArrayList = new ArrayList<>();

        //Trending data
        getData();


        //banner
        recyclerBanner = view.findViewById(R.id.banner);
        bannerArrayList = new ArrayList<>();
        bannerAdapter = new SliderAdapter(bannerArrayList, getActivity().getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerBanner.addItemDecoration(new DotsIndicatorDecoration(15,40,60,0x33000000,0xFFFF7043));
        recyclerBanner.setLayoutManager(layoutManager);

        //viewpager behavior
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerBanner);

        //event handler
        final int interval = 3000;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if (count<bannerArrayList.size()){
                    recyclerBanner.smoothScrollToPosition(count++);
                    handler.postDelayed(this, interval);
                    if (count == bannerArrayList.size()){
                        count = 0;
                    }
                }
            }
        };
        handler.postDelayed(runnable, interval);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    void addData() {
        Recom ob1 = new Recom("Akatzuki", "TOP 1 BINANCE ARTIST\n" +
                "▵Best and profitable NFT on Binance \n" +
                "https://www.instagram.com/nft.pride/" +
                "https://discord.gg/BV2gsuSz2A", R.drawable.akatzuki, R.drawable.orang);
        recomArrayList.add(ob1);
        Recom ob2 = new Recom("Bambank", "FEKIKI is the Secret Key to unlock more FEKIRA UNIVERSE benefits in the near futur..", R.drawable.akatzuki, R.drawable.naruto);
        recomArrayList.add(ob2);
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
                            Trend ob1 = new Trend(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(),base + item.getImage_path(), item.getId() );
                            trendArrayList.add(ob1);

                        }
                        if (no <= 5){
                            Banner ob = new Banner(base + item.getImage_path(), item.getId());
                            bannerArrayList.add(ob);
                        }
                        no++;
                    }
                    recyclerBanner.setAdapter(new SliderAdapter(bannerArrayList, getContext()));
                    recyclerTrend.setAdapter(new MyAdapterTrend(trendArrayList, getContext()));
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed " + response.body(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Market.CollectionResponse>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

}