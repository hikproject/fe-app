package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.PricePascaAdapter;
import gens.global.gensmasterapps.adapter.PricePraAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ProdukModel> produkModelList;
    ApiService apiService;
    Call<Category> categoryCall;
    PricePraAdapter pricePraAdapter;
    PricePascaAdapter pricePascaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getExtras().getString("category"));
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        recyclerView = findViewById(R.id.listdetail);
        produkModelList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (getIntent().getExtras().getInt("type") == 1){
            pricePraAdapter = new PricePraAdapter(this,produkModelList);
            recyclerView.setAdapter(pricePraAdapter);
            getPrabayar();
        }else{
            pricePascaAdapter = new PricePascaAdapter(this,produkModelList);
            recyclerView.setAdapter(pricePascaAdapter);
            getPascabayar();
        }
    }
    void getPrabayar(){
        JSONObject json = new JSONObject();
        try {
            json.put("category",getIntent().getExtras().getString("category"));
            categoryCall = apiService.showPra(json.toString());
            categoryCall.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    assert response.body() != null;
                    Log.d("getPrabayar", new Gson().toJson(response.body()));
                    produkModelList = response.body().getData();
                    pricePraAdapter.setProduk(produkModelList);
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void getPascabayar(){
        JSONObject json = new JSONObject();
        try {
            json.put("category",getIntent().getExtras().getString("category"));
            categoryCall = apiService.showPasca(json.toString());
            categoryCall.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    assert response.body() != null;
                    Log.d("getPrabayar", new Gson().toJson(response.body()));
                    produkModelList = response.body().getData();
                    pricePascaAdapter.setProduk(produkModelList);
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

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