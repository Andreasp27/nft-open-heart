package com.example.nft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Collection extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Collected> collectedArrayList;
    ImageView emptyI;
    TextView emptyT;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        emptyI = view.findViewById(R.id.emptyrecycler);
        emptyT = view.findViewById(R.id.textempty);

        recyclerView = view.findViewById(R.id.recyclerCollected);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        collectedArrayList = new ArrayList<>();
        addData2();

        recyclerView.setAdapter(new MyAdapterCollection(collectedArrayList, getContext()));

        if (collectedArrayList.size() == 0) {
//            Toast.makeText(getContext(), "kososng", Toast.LENGTH_SHORT).show();
            emptyI.setVisibility(View.VISIBLE);
            emptyT.setVisibility(View.VISIBLE);
        }


        return view;
    }

    void addData2() {
//        Collected ob1 = new Collected(R.drawable.orang, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob1);
//        Collected ob2 = new Collected(R.drawable.boredape, "3D Cinema Human", "25 SKS", "Bored Ape ");
//        collectedArrayList.add(ob2);
    }
}