package com.example.nft;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.MessageResponse;
import com.example.nft.api.RealPath;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCollection extends AppCompatActivity {

    ImageView backBtn, wallet;
    TextInputLayout itemName, itemPrice, itemDesc;
    ImageView imgCollection, addBtn;
    Button publish;
    Boolean image = false;
    TextView required;
    Uri imgCollectionUri;
    private Session session;
    private String access_token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        getSupportActionBar().hide();

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

        //get view
        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.item_price);
        itemDesc = findViewById(R.id.item_desc);
        imgCollection = findViewById(R.id.img_collection);
        backBtn = findViewById(R.id.btn_back_add_coll);
        wallet = findViewById(R.id.wallet_add_coll);
        addBtn = findViewById(R.id.add_img);
        publish = findViewById(R.id.publish);
        required = findViewById(R.id.required_txt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCollection.this, wallet.class);
                startActivity(intent);
            }
        });

        imgCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImgCollection.launch("image/*");

            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublishCollect();
            }
        });
    }

    ActivityResultLauncher<String> getImgCollection = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        imgCollectionUri = result;
                        imgCollection.setImageURI(result);
                        imgCollection.setPadding(0,0,0,0);
                        image = true;
                        required.setVisibility(View.INVISIBLE);
                    }
                }
            });

    public void PublishCollect(){
        if(TextUtils.isEmpty(itemName.getEditText().getText())){
            itemName.getEditText().setError("Item name is Empty");
        } else if (TextUtils.isEmpty(itemPrice.getEditText().getText())){
            itemPrice.getEditText().setError("Item price field is Empty");
        }else if (TextUtils.isEmpty(itemDesc.getEditText().getText())){
            itemDesc.getEditText().setError("Item Description field is Empty");
        }else if (image == false){
            addBtn.setColorFilter(Color.parseColor("#FF0000"));
            required.setVisibility(View.VISIBLE);

        }else {

            RequestBody nama_item = RequestBody.create(itemName.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody harga = RequestBody.create(itemPrice.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody deskripsi = RequestBody.create(itemDesc.getEditText().getText().toString(), MediaType.parse("text/plain"));

            File file = new File(new RealPath().getRealPathFromURI(this, imgCollectionUri));
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("image_path", file.getName(), requestFile);


            Call<MessageResponse> messageResponseCall = ApiClient.getUserService().createCollection("Bearer "+ access_token, nama_item, harga, deskripsi, body);
            messageResponseCall.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Create collection success", Toast.LENGTH_LONG).show();
                        refreshActivity();
                    }else{
                        Toast.makeText(getApplicationContext(), "Create collection failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Create collection failed: " + t, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void refreshActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }
}