package gens.global.gensmasterapps.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.PilihProduk;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlnActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText number;
    TextView pemilik;
    MyMessage myMessage;
    Call<Category> categoryCall;
    ApiService apiService;
    RecyclerView listProduk;
    PilihProduk pilihProduk;
    List<ProdukModel> produkModelList;
    LoadingButton loadingButton;
    Call<DefaultModel> defaultModelCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi PLN");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        number = findViewById(R.id.number);
        pemilik = findViewById(R.id.pemilik);
        loadingButton = findViewById(R.id.beli);
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pemilik.setVisibility(View.GONE);
                produkModelList.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        listProduk = findViewById(R.id.listProduk);
        produkModelList = new ArrayList<>();
        loadingButton.setOnClickListener(v->{
            if (validate(number,"Nomor meter atau KWH tidak boleh kosong")){
                if (number.getText().toString().length() < 6){
                    myMessage.Error("Masukan nomor tujuan yang sesuai");
                }else{
                    pilihProduk = new PilihProduk(this,produkModelList,number.getText().toString().trim(),
                            this,"prabayar");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    listProduk.setLayoutManager(linearLayoutManager);
                    listProduk.setAdapter(pilihProduk);
                    checkPLN();
                    JavaClass.HideButton(this,this);
                }
            }
        });
    }
    void checkPLN(){
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customer",number.getText().toString().trim());
            defaultModelCall = apiService.checkPLN(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekpln", new Gson().toJson(response.body()));
                    pemilik.setVisibility(View.VISIBLE);
                    pemilik.setText("Nama : "+response.body().getData().getName()+"\nPower : "+response.body().getData().getSegment_power()+"\nID : "+response.body().getData().getSubscriber_id());
                    getProduct();
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {
                    Log.d("cekpln", t.getMessage());
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void getProduct(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category","PLN");
            jsonObject.put("operator","PLN");
            categoryCall = apiService.SelectProduct(jsonObject.toString());
            categoryCall.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    produkModelList = response.body().getData();
                    pilihProduk.setProduk(produkModelList);
                }
                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        loadingButton.hideLoading();
    }
    public boolean validate(EditText editText, String pesan) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        myMessage.Error(pesan);
        editText.requestFocus();
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}