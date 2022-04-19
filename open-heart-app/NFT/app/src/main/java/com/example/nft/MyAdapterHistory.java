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

public class MyAdapterHistory extends RecyclerView.Adapter<MyAdapterHistory.HistoryWalletHolder> {

    private ArrayList<HistoryWallet> historyWallet;
    private Context context;

    public MyAdapterHistory(ArrayList<HistoryWallet> historyWallet, Context context) {

        this.historyWallet = historyWallet;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryWalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.historywallet, parent, false);
        return new HistoryWalletHolder(view);
    }


    @Override
    public void onBindViewHolder(HistoryWalletHolder holder, int position) {
        holder.sks.setText(historyWallet.get(position).getSks());
        holder.jumlah.setText(historyWallet.get(position).getJumlah());
        holder.image.setImageResource(historyWallet.get(position).getLogo());


    }


    @Override
    public int getItemCount() {

        return historyWallet.size();
    }

    public class HistoryWalletHolder  extends  RecyclerView.ViewHolder{
        private TextView jumlah, sks;
        private ImageView image;

        public HistoryWalletHolder(View itemView) {
            super(itemView);
            jumlah = itemView.findViewById(R.id.jumlah);
            sks = itemView.findViewById(R.id.sks);
            image = itemView.findViewById(R.id.logo);

        }
    }
}

