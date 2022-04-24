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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterCreated extends RecyclerView.Adapter<MyAdapterCreated.CreatedViewHolder> {

    private ArrayList<Created> createdArrayList;
    private Context context;


    public MyAdapterCreated(ArrayList<Created> createdArrayList, Context context) {

        this.createdArrayList = createdArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CreatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.created_item, parent, false);
        return new CreatedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CreatedViewHolder holder, int position) {
        holder.owner.setText(createdArrayList.get(position).getOwned());
        holder.nameitem.setText(createdArrayList.get(position).getItemname());
        holder.price.setText(createdArrayList.get(position).getPrice());
        Picasso.get().load(createdArrayList.get(position).getImage()).into(holder.image);

        int post = position;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemPreviewCollection.class);
                intent.putExtra("id", createdArrayList.get(post).getId());
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return createdArrayList.size();
    }

    public class CreatedViewHolder  extends  RecyclerView.ViewHolder{
        private TextView owner, nameitem, price;
        private ImageView image;
        private CardView cardView;

        public CreatedViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemcreated);
            owner = itemView.findViewById(R.id.owner);
            nameitem = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.item);

        }
    }
}
