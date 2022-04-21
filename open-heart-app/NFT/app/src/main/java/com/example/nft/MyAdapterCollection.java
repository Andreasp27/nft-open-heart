package com.example.nft;

import android.content.Context;
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

public class MyAdapterCollection extends RecyclerView.Adapter<MyAdapterCollection.CollectionViewHolder> {

    private ArrayList<Collected> collectedArrayList;
    private Context context;

    public MyAdapterCollection(ArrayList<Collected> collectedArrayList, Context context) {

        this.collectedArrayList = collectedArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.collection_item, parent, false);
        return new CollectionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        holder.creator.setText(collectedArrayList.get(position).getCreator());
        holder.nameitem.setText(collectedArrayList.get(position).getItemname());
        holder.price.setText(collectedArrayList.get(position).getPrice());
        holder.image.setImageResource(collectedArrayList.get(position).getImageCol());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "ini", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {

        return collectedArrayList.size();
    }

    public class CollectionViewHolder  extends  RecyclerView.ViewHolder{
        private TextView creator, nameitem, price;
        private ImageView image;
        private CardView cardView;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemcolection);
            creator = itemView.findViewById(R.id.creator);
            nameitem = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.item);

        }
    }
}
