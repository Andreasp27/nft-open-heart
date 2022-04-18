package com.example.nft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.nft.api.Session;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView nav;
    NavController navController;
    AppBarConfiguration appbar;
    private String access_token;
    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get access token
        access_token = getIntent().getExtras().getString("key");

        //save access token
        Bundle bundle = new Bundle();
        bundle.putString("access_token", access_token);
        session = new Session(this);
        session.setAccessToken(access_token);
        session.setBase("http://10.0.2.2:8000/");

        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

       nav = findViewById(R.id.bottomNavigationView);
//        navController = Navigation.findNavController(this, R.id.navHost);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home2, R.id.market, R.id.trending, R.id.collection, R.id.profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navHost);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(nav, navController);
//        navController.navigate(R.id.profile, bundle);



    }
}