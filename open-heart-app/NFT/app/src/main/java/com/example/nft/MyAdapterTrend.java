package com.example.nft;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapterTrend extends RecyclerView.Adapter<MyAdapterTrend.TrendViewHolder> {

    private ArrayList<Trend> trendArrayList;
    private ArrayList<Trend> trends;
    private Context context;

    public MyAdapterTrend(ArrayList<Trend> trendArrayList,Context context) {

        this.trendArrayList = trendArrayList;
        this.trends = trends;
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
        Picasso.get().load(trendArrayList.get(position).getImage()).into(holder.image);

        int post = position;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemPreview.class);
                intent.putExtra("id", trendArrayList.get(post).getId());
                view.getContext().startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {

        return trendArrayList.size();
    }

//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//                if (charString.isEmpty()){
//                    trendArrayList = trends;
//                } else {
//                    ArrayList<Trend> filteredList = new ArrayList<>();
//                    for (Trend item : trendArrayList){
//                        if (item.getItemname().toLowerCase().contains(charString.toLowerCase())){
//                            filteredList.add(item);
//                        }
//                    }
//                    trends = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = trendArrayList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                trendArrayList = (ArrayList<Trend>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//
//        return filter;
//    }




    public class TrendViewHolder  extends  RecyclerView.ViewHolder{
        private TextView creator, nameitem, price;
        private ImageView image;
        private CardView cardView;

        public TrendViewHolder(View itemView) {
            super(itemView);
            creator = itemView.findViewById(R.id.creator);
            nameitem = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.item);
            cardView = itemView.findViewById(R.id.itemcolection);

        }
    }

}
