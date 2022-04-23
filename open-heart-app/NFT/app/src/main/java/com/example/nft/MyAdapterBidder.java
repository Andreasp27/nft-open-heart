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

import com.denzcoskun.imageslider.interfaces.ItemClickListener;

import java.util.ArrayList;

public class MyAdapterBidder extends RecyclerView.Adapter<MyAdapterBidder.BidderViewHolder> {

    private ArrayList<Bidder> bidderArrayList;
    private Context context;


    public MyAdapterBidder(ArrayList<Bidder> bidderArrayList, Context context) {

        this.bidderArrayList = bidderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BidderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.bidder, parent, false);
        return new BidderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BidderViewHolder holder, int position) {
        holder.nama.setText(bidderArrayList.get(position).getName());
        holder.price.setText(bidderArrayList.get(position).getPrice());

    }


    @Override
    public int getItemCount() {

        return bidderArrayList.size();
    }

    public class BidderViewHolder  extends  RecyclerView.ViewHolder{
        private TextView nama, price;

        public BidderViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.bidder);
            price = itemView.findViewById(R.id.price);

        }
    }
}
