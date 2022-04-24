package com.example.nft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Trending extends Fragment {

    MyAdapterTopSales myAdapterTopSales;
    RecyclerView recyclerView, recyclerTop;
    ArrayList<TopSales> topSales;
    MyAdapterTrend myAdapterTrend;
    ArrayList<Trend> trendArrayList;
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
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        //get access token
        session = new Session(getActivity().getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

        //recycler view top sales
        recyclerTop = view.findViewById(R.id.recyclerTrend);
        topSales = new ArrayList<>();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        recyclerTop.setLayoutManager(layoutManager);

        myAdapterTopSales = new MyAdapterTopSales(topSales, getContext());

        recyclerView = view.findViewById(R.id.recyclerTrend2);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);
        trendArrayList = new ArrayList<>();

        getData();

        return  view;
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
                    if (no <= 3){
                        TopSales ob1 = new TopSales(base + item.getImage_path(), Integer.toString(no), item.getNama_item(), Float.toString(item.getHarga()),"+ " + Float.toString(item.getKenaikan()) + " %");
                        topSales.add(ob1);

                    }

                    Trend obj = new Trend(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), base + item.getImage_path(), item.getId());
                    trendArrayList.add(obj);
                    System.out.println("item name: " + item.getNama_item());

                    no++;
                }

                recyclerTop.setAdapter(new MyAdapterTopSales(topSales, getContext()));
                recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getContext()));
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Market.CollectionResponse>> call, Throwable t) {
            Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed: "+t, Toast.LENGTH_LONG).show();
        }
    });
}
}