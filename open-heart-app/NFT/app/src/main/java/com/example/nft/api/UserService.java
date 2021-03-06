package com.example.nft.api;

import com.example.nft.ChangePass;
import com.example.nft.Collection;
import com.example.nft.EditProfile;
import com.example.nft.HistoryWallet;
import com.example.nft.Home;
import com.example.nft.ItemPreview;
import com.example.nft.ItemPreviewCollection;
import com.example.nft.Market;
import com.example.nft.Send;
import com.example.nft.SignUp;
import com.example.nft.TopUp;
import com.example.nft.wallet;
import com.example.nft.ItemPreview;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    @POST("logout")
    Call<MessageResponse> logout(@Header("Authorization") String auth);

    @GET("profile")
    Call<EditProfile.ProfileRR> getUserProfile(@Header("Authorization") String auth);

    @Multipart
    @POST("update")
    Call<MessageResponse> updateDataProfile(@Header("Authorization") String auth,
                                                  @Part("name") RequestBody name,
                                                  @Part("email") RequestBody email,
                                                  @Part("jenis_kelamin") RequestBody jenis_kelamin,
                                                  @Part("alamat") RequestBody alamat,
                                                  @Part("nomor_telepon") RequestBody nomor_telepon,
                                                  @Part("bio") RequestBody bio,
                                                  @Part MultipartBody.Part fileImg,
                                                  @Part MultipartBody.Part fileBanner);

    @POST("updatepass")
    Call<MessageResponse> updatePass(@Header("Authorization") String auth,
                                     @Body ChangePass.changePassRequest changePassRequest);

    @Multipart
    @POST("updateimg")
    Call<EditProfile.ProfileRR> updateImageProfile(@Header("Authorization") String auth,
                                                   @Part MultipartBody.Part file);

    @GET("wallet")
    Call<wallet.walletResponse> getBalance(@Header("Authorization") String auth);

    @GET("wallet/history")
    Call<ArrayList<wallet.historyResponse>> getHistory(@Header("Authorization") String auth);

    @POST("wallet/topup")
    Call<MessageResponse> topUp(@Header("Authorization") String auth, @Body TopUp.TopUpRequest body);

    @POST("wallet/send")
    Call<MessageResponse> send(@Header("Authorization") String auth,  @Body Send.SendRequest body);

    @GET("collection")
    Call<ArrayList<Market.CollectionResponse>> getAllCollection(@Header("Authorization") String auth);

    @GET("collection/trending")
    Call<ArrayList<Market.CollectionResponse>> getAllTrending(@Header("Authorization") String auth);

    @GET("collection/mycollection")
    Call<Collection.MyCollectionResponse> getAllMyCollection(@Header("Authorization") String auth);

    @Multipart
    @POST("collection/create")
    Call<MessageResponse> createCollection(@Header("Authorization") String auth,
                                                  @Part("nama_item") RequestBody nama,
                                                  @Part("harga") RequestBody harga,
                                                  @Part("deskripsi") RequestBody deskripsi,
                                                  @Part MultipartBody.Part fileImg);

    @POST("collection/item")
    Call<Market.CollectionResponse> getCollection(@Header("Authorization") String auth,
                                                  @Body ItemPreview.IdRequest id);

    @POST("collection/bid")
    Call<MessageResponse> placeBid(@Header("Authorization") String auth,
                                   @Body ItemPreview.BidRequest bidRequest);

    @POST("collection/terimabid")
    Call<MessageResponse> acceptBidR(@Header("Authorization") String auth,
                                    @Body ItemPreviewCollection.AcceptBidRequest acceptBidRequest);

    @GET("user")
    Call<ArrayList<EditProfile.ProfileRR>> getAllCreator(@Header("Authorization") String auth);

    @POST("creator")
    Call<EditProfile.ProfileRR> getCreator(@Header("Authorization") String auth,
                                           @Body ItemPreview.IdRequest id);

    @POST("like")
    Call<MessageResponse> like(@Header("Authorization") String auth,
                               @Body ItemPreview.IdRequest id);

}
