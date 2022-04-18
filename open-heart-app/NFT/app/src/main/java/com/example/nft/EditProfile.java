package com.example.nft;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nft.api.ApiClient;
import com.example.nft.api.Session;
import com.google.android.material.textfield.TextInputLayout;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    TextInputLayout editName, editEmail, editSex, editAddress, editPhone, editBio;
    Button saveBtn;
    ImageButton backBtn, addProfileImg, addProfileBanner;
    ImageView imageProfile, imageBanner;
    private Session session;
    private String access_token;
    Uri imgUri, bannerUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        //get access token
        session = new Session(this);
        access_token = session.getAccessToken();

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

        //set profile picture
//        imageProfile.setImageResource(R.mipmap.profile);
//        Picasso.get().load("https://open-heart.herokuapp.com/images/orang.jpeg").into(imageProfile);

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


        Call<EditProfile.ProfileRR> profileRRCall = ApiClient.getUserService().getUserProfile("Bearer "+ access_token );
        profileRRCall.enqueue(new Callback<EditProfile.ProfileRR>() {
            @Override
            public void onResponse(Call<EditProfile.ProfileRR> call, Response<EditProfile.ProfileRR> response) {
                EditProfile.ProfileRR profileRR = response.body();
                editName.getEditText().setText(profileRR.getName());
                editEmail.getEditText().setText(profileRR.getEmail());
                editSex.getEditText().setText(profileRR.getJenis_kelamin());
                editAddress.getEditText().setText(profileRR.getAlamat());
                editPhone.getEditText().setText(profileRR.getNomor_telepon());
                editBio.getEditText().setText(profileRR.getBio());
            }

            @Override
            public void onFailure(Call<EditProfile.ProfileRR> call, Throwable t) {
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



    ActivityResultLauncher<String> getImgProfile = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        imgUri = result;
                        imageProfile.setImageURI(result);
                    }
                }
            });

    ActivityResultLauncher<String> getImgBanner = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        bannerUri = result;
                        imageBanner.setImageURI(result);
                    }
                }
            });


    public class ProfileRR{
        private String name;
        private String email;
        private String jenis_kelamin;
        private String alamat;
        private String nomor_telepon;
        private String bio;

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

            ProfileRR profileRR = new ProfileRR();
            profileRR.setName(editName.getEditText().getText().toString());
            profileRR.setEmail(editEmail.getEditText().getText().toString());
            profileRR.setJenis_kelamin(editSex.getEditText().getText().toString());
            profileRR.setAlamat(editAddress.getEditText().getText().toString());
            profileRR.setNomor_telepon(editPhone.getEditText().getText().toString());
            profileRR.setBio(editBio.getEditText().getText().toString());


            Call<ProfileRR> profileRRCall = ApiClient.getUserService().updateDataProfile("Bearer "+ access_token, profileRR);
            profileRRCall.enqueue(new Callback<ProfileRR>() {
                @Override
                public void onResponse(Call<ProfileRR> call, Response<ProfileRR> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Update data success", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Update data failed", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ProfileRR> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Update data failed", Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}