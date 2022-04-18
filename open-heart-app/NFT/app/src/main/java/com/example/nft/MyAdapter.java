package com.example.nft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecomViewHolder> {

    private ArrayList<Recom> recomArrayList;
    private Context context;

    public MyAdapter(ArrayList<Recom> recomArrayList, Context context) {

        this.recomArrayList = recomArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_item, parent, false);
        return new RecomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecomViewHolder holder, int position) {
        holder.creator.setText(recomArrayList.get(position).getCreator());
        holder.desc.setText(recomArrayList.get(position).getDescription());
        holder.imgcreator.setImageResource(recomArrayList.get(position).getImagecrea());
        holder.imgbanner.setImageResource(recomArrayList.get(position).getImagebann());

    }


    @Override
    public int getItemCount() {

        return recomArrayList.size();
    }

    public class RecomViewHolder  extends  RecyclerView.ViewHolder{
        private TextView creator, desc;
        private ImageView imgcreator, imgbanner;

        public RecomViewHolder(View itemView) {
            super(itemView);
            creator = itemView.findViewById(R.id.nama);
            desc = itemView.findViewById(R.id.desc);
            imgcreator = itemView.findViewById(R.id.creator);
            imgbanner = itemView.findViewById(R.id.recommend);

        }
    }
}
