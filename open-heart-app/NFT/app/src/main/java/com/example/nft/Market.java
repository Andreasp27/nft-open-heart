package com.example.nft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class Market extends Fragment {

    RecyclerView recyclerView;
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
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        //get access token
        session = new Session(getActivity().getApplicationContext());
        access_token = session.getAccessToken();
        base = session.getBase();

        recyclerView = view.findViewById(R.id.recyclerMarket);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager2);
        trendArrayList = new ArrayList<>();

//        addData2();
        getData();


        return view;
    }

    void getData(){
        Call<ArrayList<CollectionResponse>> collectionResponseCall = ApiClient.getUserService().getAllCollection("Bearer "+ access_token);
        collectionResponseCall.enqueue(new Callback<ArrayList<CollectionResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CollectionResponse>> call, Response<ArrayList<CollectionResponse>> response) {
                if (response.isSuccessful()){
                    ArrayList<CollectionResponse> data = response.body();
                    for (CollectionResponse item : data){
                        Trend obj = new Trend(item.getNama_item(), Float.toString(item.getHarga()), item.getPembuat(), base + item.getImage_path());
                        trendArrayList.add(obj);
                        System.out.println("item name: " + item.getNama_item());
                    }

                    recyclerView.setAdapter(new MyAdapterTrend(trendArrayList, getContext()));
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CollectionResponse>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Fetch data failed: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class CollectionResponse{
        private String pembuat;
        private String nama_item;
        private float harga;
        private String deskripsi;
        private String image_path;
        private float kenaikan;

        public float getKenaikan() {
            return kenaikan;
        }

        public void setKenaikan(float kenaikan) {
            this.kenaikan = kenaikan;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getPembuat() {
            return pembuat;
        }

        public void setPembuat(String pembuat) {
            this.pembuat = pembuat;
        }

        public String getNama_item() {
            return nama_item;
        }

        public void setNama_item(String nama_item) {
            this.nama_item = nama_item;
        }

        public float getHarga() {
            return harga;
        }

        public void setHarga(float harga) {
            this.harga = harga;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }
    }
}