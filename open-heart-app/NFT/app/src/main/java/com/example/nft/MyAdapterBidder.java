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

import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        int post = position;
        holder.placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "berhasil yey", Toast.LENGTH_LONG).show();
                acceptBid(bidderArrayList.get(post).getAccess_token(), bidderArrayList.get(post).getUser_number(), bidderArrayList.get(post).getId() );

            }
        });

    }



    @Override
    public int getItemCount() {

        return bidderArrayList.size();
    }

    public class BidderViewHolder  extends  RecyclerView.ViewHolder{
        private TextView nama, price;
        private Button placeBid;

        public BidderViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.bidder);
            price = itemView.findViewById(R.id.price);
            placeBid = itemView.findViewById(R.id.acceptbid);

        }
    }

    public void acceptBid(String access_token, String nomor_user, int id){
        ItemPreviewCollection.AcceptBidRequest acceptBidRequest = new ItemPreviewCollection.AcceptBidRequest();
        acceptBidRequest.setNomor_user(nomor_user);
        acceptBidRequest.setId_koleksi(id);
        Call<MessageResponse> acceptBidRequestCall = ApiClient.getUserService().acceptBidR("Bearer " + access_token, acceptBidRequest);
        acceptBidRequestCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("success")){
                        Toast.makeText(context, "Bid Accepted", Toast.LENGTH_LONG).show();
                        new ItemPreviewCollection().refreshActivity();
                    }else{
                        Toast.makeText(context, "Bid Failed", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "Bid Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(context, "Bid Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void refreshActivity() {

    }
}
