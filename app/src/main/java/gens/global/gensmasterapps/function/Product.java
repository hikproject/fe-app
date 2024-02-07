package gens.global.gensmasterapps.function;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product {
    List<ProdukModel> produkModelList;
    Call<Category> categoryCall;
    ApiService apiService;
    Context mContext;
    public Product(Context context){
        this.mContext = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
        produkModelList = new ArrayList<>();
    }
    public void EMoney(Spinner userSpinner){
        categoryCall = apiService.getEmoney();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getBrand());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinner.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void Voucher(Spinner userSpinner){
        categoryCall = apiService.getVoucher();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getBrand());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinner.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void Tv(Spinner userSpinner){
        categoryCall = apiService.getTv();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getBrand());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinner.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void BPJSKer(Spinner userSpinners){
        categoryCall = apiService.getBpjsKer();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getName().replace("Bpjs Ketenagakerjaan",""));
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinners.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void TvPasca(Spinner userSpinners){
        categoryCall = apiService.TvPasca();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getName());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinners.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void Tagihan(Spinner userSpinners){
        categoryCall = apiService.Tagihan();
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                assert response.body() != null;
                produkModelList = response.body().getData();
                List<String> brandList = new ArrayList<>();
                for (ProdukModel item : produkModelList) {
                    brandList.add(item.getName());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                userSpinners.setAdapter(brandAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    public void checkTagihan(Spinner userSpinners,String category){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category",category);
            categoryCall = apiService.checkTagihan(jsonObject.toString());
            categoryCall.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    assert response.body() != null;
                    produkModelList = response.body().getData();
                    List<String> brandList = new ArrayList<>();
                    for (ProdukModel item : produkModelList) {
                        brandList.add(item.getName());
                    }
                    ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner_dropdown_item, R.id.custom_text_view, brandList);
                    userSpinners.setAdapter(brandAdapter);
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
