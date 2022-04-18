package com.example.nft.api;

import com.example.nft.ChangePass;
import com.example.nft.EditProfile;
import com.example.nft.SignUp;
import com.example.nft.profilOption;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("register")
    Call<SignUp.RegisterResponse> userRegister(@Body SignUp.RegisterRequest registerRequest);

    @GET("profile")
    Call<EditProfile.ProfileRR> getUserProfile(@Header("Authorization") String auth);

    @Multipart
    @POST("update")
    Call<EditProfile.ProfileRR> updateDataProfile(@Header("Authorization") String auth,
                                                  @Part("name") RequestBody name,
                                                  @Part("email") RequestBody email,
                                                  @Part("jenis_kelamin") RequestBody jenis_kelamin,
                                                  @Part("alamat") RequestBody alamat,
                                                  @Part("nomor_telepon") RequestBody nomor_telepon,
                                                  @Part("bio") RequestBody bio,
                                                  @Part MultipartBody.Part fileImg,
                                                  @Part MultipartBody.Part fileBanner);

    @POST("logout")
    Call<MessageResponse> logout(@Header("Authorization") String auth);

    @POST("updatepass")
    Call<MessageResponse> updatePass(@Header("Authorization") String auth, @Body ChangePass.changePassRequest changePassRequest);

    @Multipart
    @POST("updateimg")
    Call<EditProfile.ProfileRR> updateImageProfile(@Header("Authorization") String auth,
                                                   @Part MultipartBody.Part file
                                                   );
}
