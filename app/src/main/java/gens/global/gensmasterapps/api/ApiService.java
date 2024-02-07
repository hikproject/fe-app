package gens.global.gensmasterapps.api;

import java.util.List;

import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.model.InvoiceModel;
import gens.global.gensmasterapps.model.MitraModel;
import gens.global.gensmasterapps.model.TrxModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("auth")
    Call<DefaultModel> loginUser(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("register")
    Call<DefaultModel> signupUser(@Body String body);

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("get")
    Call<DefaultModel> getUsers(@Body String body);

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("get-prabayar")
    Call<Category> getPrabayar();

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("get-pascabayar")
    Call<Category> getPascabayar();

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("show-prabayar")
    Call<Category> showPra(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("show-pasca")
    Call<Category> showPasca(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("lap-transaksi")
    Call<TrxModel> getTransaction(@Body String body);

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("pay-prabayar")
    Call<Category> SelectProduct(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("check-pln")
    Call<DefaultModel> checkPLN(@Body String body);

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("emoney")
    Call<Category> getEmoney();
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("voucher")
    Call<Category> getVoucher();
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("tv-prabayar")
    Call<Category> getTv();
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("bpjsker")
    Call<Category> getBpjsKer();
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("tvpasca")
    Call<Category> TvPasca();

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("tagihan")
    Call<Category> Tagihan();

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("check-tagihan")
    Call<Category> checkTagihan(@Body String body);

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("topup")
    Call<Category> TopUpSaldo(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("invoice")
    Call<InvoiceModel> Invoice(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @GET("broadcast")
    Call<DefaultModel> broadcast();

    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("mitra")
    Call<List<MitraModel>> Downline(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("cek-presiden")
    Call<DefaultModel> CheckPresiden(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("upload-presiden")
    Call<DefaultModel> UploadImagesPresiden(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("save-presiden")
    Call<DefaultModel> savePresiden(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("upload-caleg")
    Call<DefaultModel> UploadImagesCaleg(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("save-caleg")
    Call<DefaultModel> saveCaleg(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("cek-caleg")
    Call<DefaultModel> CheckCaleg(@Body String body);
    @Headers({
            "Content-Type: application/json",
            "X-Api-Key:31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4"
    })
    @POST("belipulsa")
    Call<DefaultModel> BeliPulsa(@Body String body);
}
