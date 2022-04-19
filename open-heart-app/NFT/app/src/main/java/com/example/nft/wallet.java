package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class wallet extends AppCompatActivity {

    private MyAdapterHistory myAdapterHistory;
    private RecyclerView recyclerView;
    private ArrayList<HistoryWallet> historyWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerhistory);
        historyWallet = new ArrayList<>();

        myAdapterHistory = new MyAdapterHistory(historyWallet, this);
        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new MyAdapterHistory(historyWallet, this));


    }

    void addData() {
        HistoryWallet ob1 = new HistoryWallet("SKS", "+ 230", R.drawable.ic_ethereum_eth);
        historyWallet.add(ob1);
        HistoryWallet ob2 = new HistoryWallet("SKS", "+ 200", R.drawable.ic_ethereum_eth);
        historyWallet.add(ob2);
        HistoryWallet ob3 = new HistoryWallet("SKS", "- 3020", R.drawable.ic_ethereum_eth);
        historyWallet.add(ob3);
        HistoryWallet ob4 = new HistoryWallet("SKS", "- 3020", R.drawable.ic_ethereum_eth);
        historyWallet.add(ob4);
    }
}