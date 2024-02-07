package gens.global.gensmasterapps.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.MoreAdapter;
import gens.global.gensmasterapps.adapter.MoresAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.SpacesItemDecoration;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LainnyaActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<ProdukModel> produkModelList,produkModelLists;
    MoreAdapter moreAdapter;
    MoresAdapter moresAdapter;
    RecyclerView listMore,listMores;
    ApiService apiService;
    Call<Category> categoryCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lainnya);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Semua Produk");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        listMore = findViewById(R.id.listMore);
        produkModelList = new ArrayList<>();
        moreAdapter = new MoreAdapter(this,produkModelList);
        listMore.setLayoutManager(new GridLayoutManager(this,2));
        listMore.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_padding)));
        listMore.setAdapter(moreAdapter);
        getData();

        listMores = findViewById(R.id.listMores);
        produkModelLists = new ArrayList<>();
        moresAdapter = new MoresAdapter(this,produkModelList);
        listMores.setLayoutManager(new GridLayoutManager(this,2));
        listMores.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_padding)));
        listMores.setAdapter(moresAdapter);
        getPasca();
    }
    private void getData(){
        categoryCall = apiService.getPrabayar();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                produkModelList = response.body().getData();
                moreAdapter.setProduk(produkModelList);
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    private void getPasca(){
        categoryCall = apiService.getPascabayar();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                produkModelLists.addAll(response.body().getData());
                moresAdapter.setProduk(produkModelLists);
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
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