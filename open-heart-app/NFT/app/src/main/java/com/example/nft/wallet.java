package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wallet extends AppCompatActivity {

    private MyAdapterHistory myAdapterHistory;
    private RecyclerView recyclerView;
    private ArrayList<HistoryWallet> historyWallet;
    private String access_token;
    private Session session;
    List<String> jumlah = new ArrayList<>();
    ImageView btnsend, btntopup;
    ImageButton back;
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

        history(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


//        Toast.makeText(getApplicationContext(), (CharSequence) jumlah, Toast.LENGTH_LONG).show();

        //get view item
        balance = findViewById(R.id.balance);

        //get balance
        balance();

        btnsend = findViewById(R.id.imageView3);
        btntopup = findViewById(R.id.imageView2);
        back = findViewById(R.id.btn_back_wallet);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Send.class);
                startActivity(intent);
            }
        });

        btntopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TopUp.class);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void addData() {
//        HistoryWallet ob1 = new HistoryWallet("SKS", "+ 230", R.drawable.ic_ethereum_eth);
//        historyWallet.add(ob1);
//        HistoryWallet ob2 = new HistoryWallet("SKS", "+ 200", R.drawable.ic_ethereum_eth);
//        historyWallet.add(ob2);
//        HistoryWallet ob3 = new HistoryWallet("SKS", "- 3020", R.drawable.ic_ethereum_eth);
//        historyWallet.add(ob3);
//        HistoryWallet ob4 = new HistoryWallet("SKS", "- 3020", R.drawable.ic_ethereum_eth);
//        historyWallet.add(ob4);
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

    public void history(RecyclerView.LayoutManager layoutManager){
        Call<ArrayList<historyResponse>> responseBodyCall = ApiClient.getUserService().getHistory("Bearer "+ access_token);
        responseBodyCall.enqueue(new Callback<ArrayList<historyResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<historyResponse>> call, Response<ArrayList<historyResponse>> response) {
                if (response.isSuccessful()) {
                    ArrayList<historyResponse> data = response.body();
                    String sign;
                    for (historyResponse item : data) {
                        jumlah.add(Float.toString(item.getJumlah()));
                        System.out.println(item.getJumlah());
                        if (item.getStatus().equals("masuk")) {
                            sign = "+";
                        } else {
                            sign = "-";
                        }
                        HistoryWallet obj = new HistoryWallet("SKS",sign + item.getJumlah(), R.drawable.ic_ethereum_eth);
                        historyWallet.add(obj);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new MyAdapterHistory(historyWallet, getApplicationContext()));
                }else{
                    Toast.makeText(getApplicationContext(), "Fetch data history failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<historyResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data history failed" + t, Toast.LENGTH_LONG).show();
            }
        });


    }

    public class historyResponse{
        private float jumlah;
        private String status;

        public float getJumlah() {
            return jumlah;
        }

        public void setJumlah(float jumlah) {
            this.jumlah = jumlah;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}