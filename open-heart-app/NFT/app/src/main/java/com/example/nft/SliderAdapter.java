package com.example.nft;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.BannerViewHolder> {

    private ArrayList<Banner> bannerArrayList;
    private Context context;


    public SliderAdapter(ArrayList<Banner> bannerArrayList, Context context) {

        this.bannerArrayList = bannerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.banner_layout, parent, false);
        return new BannerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
//        holder.imageView.setImageResource(bannerArrayList.get(position).getImageUrl());
        Picasso.get().load(bannerArrayList.get(position).getImageUrl()).into(holder.imageView);

        int post = position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemPreview.class);
                intent.putExtra("id", bannerArrayList.get(post).getId());
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {

        return bannerArrayList.size();
    }

    public class BannerViewHolder  extends  RecyclerView.ViewHolder{
        private ImageView imageView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner);

        }
    }

}
