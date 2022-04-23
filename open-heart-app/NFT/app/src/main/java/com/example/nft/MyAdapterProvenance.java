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

public class MyAdapterProvenance extends RecyclerView.Adapter<MyAdapterProvenance.ProvenanceViewHolder> {

    private ArrayList<Provenance> provenanceArrayList;
    private Context context;


    public MyAdapterProvenance(ArrayList<Provenance> provenanceArrayList, Context context) {

        this.provenanceArrayList = provenanceArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProvenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.provenance, parent, false);
        return new ProvenanceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProvenanceViewHolder holder, int position) {
        holder.nama.setText(provenanceArrayList.get(position).getName());
        holder.status.setText(provenanceArrayList.get(position).getStatus());
        holder.price.setText(provenanceArrayList.get(position).getPrice());
        holder.time.setText(provenanceArrayList.get(position).getTime());

    }


    @Override
    public int getItemCount() {

        return provenanceArrayList.size();
    }

    public class ProvenanceViewHolder  extends  RecyclerView.ViewHolder{
        private TextView nama, status, price, time;

        public ProvenanceViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            status = itemView.findViewById(R.id.status);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);

        }
    }
}
