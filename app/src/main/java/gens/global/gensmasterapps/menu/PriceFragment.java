package gens.global.gensmasterapps.menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.ProdukAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PriceFragment extends Fragment {
    List<ProdukModel> produkModelList;
    ProdukAdapter produkAdapter;
    RecyclerView listPrabayar;
    ApiService apiService;
    Call<Category> categoryCall;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_price, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        listPrabayar = root.findViewById(R.id.listPrabayar);
        produkModelList = new ArrayList<>();
        produkAdapter = new ProdukAdapter(requireContext(), produkModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        listPrabayar.setLayoutManager(linearLayoutManager);
        listPrabayar.setAdapter(produkAdapter);
        getData();
        getPasca();
        return root;
    }
    private void getData(){
        categoryCall = apiService.getPrabayar();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                produkModelList = response.body().getData();
                produkAdapter.setProduk(produkModelList);
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
                produkModelList.addAll(response.body().getData());
                produkAdapter.setProduk(produkModelList);
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
}