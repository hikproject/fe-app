package gens.global.gensmasterapps.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
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
import gens.global.gensmasterapps.function.GetOperator;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.Product;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransaksiPrabayarFragment extends Fragment {
    Toolbar toolbar;
    String category;
    EditText phone;
    GetOperator getOperator;
    TextView operator;
    LoadingButton loadingButton;
    MyMessage myMessage;
    Call<Category> categoryCall;
    ApiService apiService;
    RecyclerView listProduk;
    PilihProduk pilihProduk;
    List<ProdukModel> produkModelList;
    Spinner spinner;
    Product product;
    String nama_operator;
    Call<DefaultModel> defaultModelCall;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_transaksi_prabayar, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Bundle arguments = getArguments();
        toolbar = root.findViewById(R.id.toolbar);
        assert arguments != null;
        category = arguments.getString("category");
        toolbar.setTitle(category);
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        apiService = RetrofitClient.getClient().create(ApiService.class);
        phone = root.findViewById(R.id.phone);
        operator = root.findViewById(R.id.operator);
        getOperator = new GetOperator(requireContext());
        loadingButton = root.findViewById(R.id.beli);
        myMessage = new MyMessage(requireContext());
        spinner = root.findViewById(R.id.spinner);
        product = new Product(requireContext());
        switch (category){
            case "Pulsa":
            case "Data":
            case "Masa Aktif":
            case "Paket SMS & Telpon":
                spinner.setVisibility(View.GONE);
                phone.setHint("No. Hnadphone");
                break;
            case "PLN":
                spinner.setVisibility(View.GONE);
                phone.setHint("No. Meteran / KWH");
                break;
            case "E-Money":
                spinner.setVisibility(View.VISIBLE);
                phone.setHint("No. Pelanggan");
                product.EMoney(spinner);
                break;
            case "Voucher":
                spinner.setVisibility(View.VISIBLE);
                product.Voucher(spinner);
                phone.setHint("No. Pelanggan");
                break;
            case "TV":
                spinner.setVisibility(View.VISIBLE);
                product.Tv(spinner);
                phone.setHint("No. Pelanggan");
                break;
        }
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                operator.setVisibility(View.GONE);
                produkModelList.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        listProduk = root.findViewById(R.id.listProduk);
        produkModelList = new ArrayList<>();
        loadingButton.setOnClickListener(v->{
            if (validate(phone,"Nomor tujuan pengisian tidak boleh kosong")){
                if (phone.getText().toString().length() < 6){
                    myMessage.Error("Masukan nomor tujuan yang sesuai");
                }else{
                    if (category.matches("Pulsa") || category.matches("Data")
                            || category.matches("Paket SMS & Telpon")
                            || category.matches("Masa Aktif")){
                        String oprt = getOperator.showOperator(phone.getText().toString().substring(0,4));
                        operator.setVisibility(View.VISIBLE);
                        operator.setText(oprt);
                        nama_operator = operator.getText().toString().trim();
                    }else{
                        nama_operator = String.valueOf(spinner.getSelectedItem());
                    }
                    pilihProduk = new PilihProduk(requireContext(),
                            produkModelList,phone.getText().toString().trim(),requireActivity(),
                            "prabayar");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    listProduk.setLayoutManager(linearLayoutManager);
                    listProduk.setAdapter(pilihProduk);
                    if (category.matches("PLN")){
                        checkPLN();
                        nama_operator = "PLN";
                    }else {
                        getProduct();
                    }

                    JavaClass.HideButton(requireContext(),requireActivity());
                }
            }
        });
       return root;
    }
    void checkPLN(){
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customer",phone.getText().toString().trim());
            defaultModelCall = apiService.checkPLN(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekpln", new Gson().toJson(response.body()));
                    operator.setVisibility(View.VISIBLE);
                    operator.setText("Nama : "+response.body().getData().getName()+"\nPower : "+response.body().getData().getSegment_power()+"\nID : "+response.body().getData().getSubscriber_id());
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
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category",category);
            jsonObject.put("operator",nama_operator);
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


}