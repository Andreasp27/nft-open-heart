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

public class MyAdapterTrend extends RecyclerView.Adapter<MyAdapterTrend.TrendViewHolder> {

    private ArrayList<Trend> trendArrayList;
    private Context context;

    public MyAdapterTrend(ArrayList<Trend> trendArrayList, Context context) {

        this.trendArrayList = trendArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trend_item, parent, false);
        return new TrendViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TrendViewHolder holder, int position) {
        holder.creator.setText(trendArrayList.get(position).getCreator());
        holder.nameitem.setText(trendArrayList.get(position).getItemname());
        holder.price.setText(trendArrayList.get(position).getPrice());
        holder.image.setImageResource(trendArrayList.get(position).getImage());


    }


    @Override
    public int getItemCount() {

        return trendArrayList.size();
    }

    public class TrendViewHolder  extends  RecyclerView.ViewHolder{
        private TextView creator, nameitem, price;
        private ImageView image;

        public TrendViewHolder(View itemView) {
            super(itemView);
            creator = itemView.findViewById(R.id.creator);
            nameitem = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.item);

        }
    }
}