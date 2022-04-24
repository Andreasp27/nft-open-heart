package com.example.nft;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(new Intent(context, CreatorPage.class));
            }
        });
    }


    @Override
    public int getItemCount() {

        return recomArrayList.size();
    }

    public class RecomViewHolder  extends  RecyclerView.ViewHolder{
        private TextView creator, desc;
        private ImageView imgcreator, imgbanner;
        private CardView cardView;

        public RecomViewHolder(View itemView) {
            super(itemView);
            creator = itemView.findViewById(R.id.nama);
            desc = itemView.findViewById(R.id.desc);
            imgcreator = itemView.findViewById(R.id.creator);
            imgbanner = itemView.findViewById(R.id.recommend);
            cardView = itemView.findViewById(R.id.recomcreator);

        }
    }
}
