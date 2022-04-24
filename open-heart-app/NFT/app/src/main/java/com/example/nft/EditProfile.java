package com.example.nft;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.RealPath;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    TextInputLayout editName, editEmail, editSex, editAddress, editPhone, editBio;
    ImageButton addProfileImg, addProfileBanner;
    ImageView backBtn;
    private Integer statusImg = 0, statusBanner = 0;
    ImageView imageProfile, imageBanner, imageWallet;
    private String access_token, base;
    private Session session;
    Uri imgUri, bannerUri;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();
        base = session.getBase();

        getSupportActionBar().hide();

        editName = findViewById(R.id.profile_name);
        editEmail = findViewById(R.id.profile_email);
        editSex = findViewById(R.id.profile_sex);
        editAddress =  findViewById(R.id.profile_address);
        editPhone = findViewById(R.id.profile_phone);
        editBio = findViewById(R.id.profile_bio);
        saveBtn = findViewById(R.id.btn_profile);
        addProfileImg = findViewById(R.id.add_profile_img);
        addProfileBanner = findViewById(R.id.add_profile_banner);
        backBtn = findViewById(R.id.btn_back);
        imageProfile = findViewById(R.id.profile_img);
        imageBanner = findViewById(R.id.profile_banner);
        imageWallet = findViewById(R.id.wallet_profile);


        //add image profile
        addProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImgProfile.launch("image/*");
            }
        });

        addProfileBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImgBanner.launch("image/*");
            }
        });

        imageWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, wallet.class);
                startActivity(intent);
            }
        });

        Call<ProfileRR> profileRRCall = ApiClient.getUserService().getUserProfile("Bearer "+ access_token );
        profileRRCall.enqueue(new Callback<ProfileRR>() {
            @Override
            public void onResponse(Call<ProfileRR> call, Response<ProfileRR> response) {
                ProfileRR profileRR = response.body();
                editName.getEditText().setText(profileRR.getName());
                editEmail.getEditText().setText(profileRR.getEmail());
                editSex.getEditText().setText(profileRR.getJenis_kelamin());
                editAddress.getEditText().setText(profileRR.getAlamat());
                editPhone.getEditText().setText(profileRR.getNomor_telepon());
                editBio.getEditText().setText(profileRR.getBio());
                if (profileRR.getGambar_path() != null){
                    Picasso.get().load(base + profileRR.getGambar_path()).into(imageProfile);
                }
                if (profileRR.getBanner_path() !=null){
                    Picasso.get().load(base + profileRR.getBanner_path()).into(imageBanner);
                }
            }

            @Override
            public void onFailure(Call<ProfileRR> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch data failed", Toast.LENGTH_LONG).show();
            }
        });

        //save profile
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        //back to profile option page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //get image profile from storage
    ActivityResultLauncher<String> getImgProfile = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        imgUri = result;
                        imageProfile.setImageURI(result);
                        statusImg = 1;
                    }
                }
            });

    //get image banner from storage
    ActivityResultLauncher<String> getImgBanner = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        bannerUri = result;
                        imageBanner.setImageURI(result);
                        statusBanner = 1;
                    }
                }
            });

    public class ProfileRR{
        private int id;
        private String name;
        private String email;
        private String jenis_kelamin;
        private String alamat;
        private String nomor_telepon;
        private String bio;
        private String gambar_path;
        private String banner_path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBanner_path() { return banner_path; }

        public void setBanner_path(String banner_path) {this.banner_path = banner_path;}

        public String getGambar_path() { return gambar_path;}

        public void setGambar_path(String gambar_path) { this.gambar_path = gambar_path; }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public void setJenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getNomor_telepon() {
            return nomor_telepon;
        }

        public void setNomor_telepon(String nomor_telepon) {
            this.nomor_telepon = nomor_telepon;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }
    }

    public void save(){
        if(TextUtils.isEmpty(editName.getEditText().getText())){
            editName.getEditText().setError("Name field is Empty");
        } else if (TextUtils.isEmpty(editEmail.getEditText().getText())){
            editEmail.getEditText().setError("Email field is Empty");
        }else if (TextUtils.isEmpty(editSex.getEditText().getText())){
            editSex.getEditText().setError("Sex field is Empty");
        }else if (TextUtils.isEmpty(editAddress.getEditText().getText())){
            editAddress.getEditText().setError("Address field is Empty");
        }else if (TextUtils.isEmpty(editPhone.getEditText().getText())){
            editPhone.getEditText().setError("Phone Number Filed is Empty");
        }else if (TextUtils.isEmpty(editBio.getEditText().getText())){
            editBio.getEditText().setError("BIO Filed is Empty");
        }else {
            RequestBody name = RequestBody.create(editName.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody email = RequestBody.create(editEmail.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody jenis_kelamin = RequestBody.create(editSex.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody alamat = RequestBody.create(editAddress.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody nomor_telepon = RequestBody.create(editPhone.getEditText().getText().toString(), MediaType.parse("text/plain"));
            RequestBody bio = RequestBody.create(editBio.getEditText().getText().toString(), MediaType.parse("text/plain"));

            MultipartBody.Part body = null;
            MultipartBody.Part body2 = null;

            //set image profil for update
            if(statusImg == 1){
                File file = new File(new RealPath().getRealPathFromURI(this, imgUri));
                RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
                body = MultipartBody.Part.createFormData("gambar_path", file.getName(), requestFile);
            }

            //set image profil for update
            if(statusBanner == 1){
                File file2 = new File(new RealPath().getRealPathFromURI(this, bannerUri));
                RequestBody requestFile = RequestBody.create(file2, MediaType.parse("image/*"));
                body2 = MultipartBody.Part.createFormData("banner_path", file2.getName(), requestFile);
            }


            Call<ProfileRR> profileRRCall = ApiClient.getUserService().updateDataProfile("Bearer "+ access_token, name,email,jenis_kelamin,alamat,nomor_telepon,bio, body, body2);
            profileRRCall.enqueue(new Callback<ProfileRR>() {
                @Override
                public void onResponse(Call<ProfileRR> call, Response<ProfileRR> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Update data success", Toast.LENGTH_LONG).show();
                        statusBanner = 0;
                        statusImg = 0;
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Update data failed", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ProfileRR> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Update data failed" + t, Toast.LENGTH_LONG).show();
                }
            });
        }
    }



}