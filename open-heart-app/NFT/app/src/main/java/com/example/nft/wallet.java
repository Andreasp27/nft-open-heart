package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wallet extends AppCompatActivity {

    private MyAdapterHistory myAdapterHistory;
    private RecyclerView recyclerView;
    private ArrayList<HistoryWallet> historyWallet;
    private String access_token;
    private Session session;
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().hide();

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

        recyclerView = findViewById(R.id.recyclerhistory);
        historyWallet = new ArrayList<>();

        myAdapterHistory = new MyAdapterHistory(historyWallet, this);
        addData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapterHistory(historyWallet, this));

        //get view item
        balance = findViewById(R.id.balance);

        //get balance
        balance();


    }
    //andreas gibran nethanel paat

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

    public class walletResponse{
        private float saldo;

        public float getSaldo() {
            return saldo;
        }

        public void setSaldo(float saldo) {
            this.saldo = saldo;
        }
    }

    public void balance(){
        Call<walletResponse> walletResponseCall = ApiClient.getUserService().getBalance("Bearer "+ access_token);
        walletResponseCall.enqueue(new Callback<walletResponse>() {
            @Override
            public void onResponse(Call<walletResponse> call, Response<walletResponse> response) {

                if (response.isSuccessful()){
                    balance.setText(Float.toString(response.body().getSaldo()));
                    Toast.makeText(getApplicationContext(), "Fetch data success", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<walletResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed" + t, Toast.LENGTH_LONG).show();
            }
        });

    }
}