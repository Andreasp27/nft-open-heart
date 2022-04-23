package com.example.nft;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {
    private RecyclerView recyclerView, recyclerTrend;
    private MyAdapter adapter;
    private MyAdapterTrend adapterTrend;
    private ArrayList<Recom> recomArrayList;
    private ArrayList<Trend> trendArrayList;
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

        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.banner);
        ArrayList <SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.orang, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.naruto, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.akatzuki, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);

        more = view.findViewById(R.id.more_trending);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.popBackStack(R.id.home2, true);
                controller.navigate(R.id.trending);
            }
        });

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

//    void addData2() {
//        Trend ob1 = new Trend("Bored APE #1003", "25 SKS", "Bored APE", R.drawable.orang);
//        trendArrayList.add(ob1);
//        Trend ob2 = new Trend("Bored APE #1005", "37 SKS", "Bored APE", R.drawable.naruto);
//        trendArrayList.add(ob2);
//    }

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
                            Trend ob1 = new Trend(item.getNama_item(), Float.toString(item.getHarga()), "Bored APE",base + item.getImage_path());
                            trendArrayList.add(ob1);

                        }
                        no++;
                    }
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