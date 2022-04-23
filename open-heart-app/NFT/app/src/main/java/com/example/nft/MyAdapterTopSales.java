package com.example.nft;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterTopSales extends RecyclerView.Adapter<MyAdapterTopSales.TopSalesViewHolder> {

    private ArrayList<TopSales> topSalesArrayList;
    private Context context;

    public MyAdapterTopSales(ArrayList<TopSales> topSalesArrayList, Context context) {

        this.topSalesArrayList = topSalesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopSalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.top_item, parent, false);
        return new TopSalesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TopSalesViewHolder holder, int position) {
        holder.noitem.setText(topSalesArrayList.get(position).getNoitem());
        holder.nameitem.setText(topSalesArrayList.get(position).getItemname());
        holder.price.setText(topSalesArrayList.get(position).getPrice());
        holder.growth.setText(topSalesArrayList.get(position).getGrowth());
        Picasso.get().load(topSalesArrayList.get(position).getImage()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(context, ItemPreview.class));
            }
        });

    }


    @Override
    public int getItemCount() {

        return topSalesArrayList.size();
    }

    public class TopSalesViewHolder  extends  RecyclerView.ViewHolder{
        private TextView noitem, nameitem, price, growth;
        private ImageView image;
        private CardView cardView;

        public TopSalesViewHolder(View itemView) {
            super(itemView);
            noitem = itemView.findViewById(R.id.noitem);
            nameitem = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.price);
            growth = itemView.findViewById(R.id.growth);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.topcard);

        }
    }
}
