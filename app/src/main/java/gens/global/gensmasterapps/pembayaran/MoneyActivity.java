package gens.global.gensmasterapps.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;

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
import gens.global.gensmasterapps.function.Product;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.ProdukModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyActivity extends AppCompatActivity {
    Toolbar toolbar;
    Product product;
    Spinner spinner;
    EditText phone;
    LoadingButton loadingButton;
    MyMessage myMessage;
    Call<Category> categoryCall;
    ApiService apiService;
    RecyclerView listProduk;
    PilihProduk pilihProduk;
    List<ProdukModel> produkModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("E-Money");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        product = new Product(this);
        spinner = findViewById(R.id.spinner);
        product.EMoney(spinner);
        phone = findViewById(R.id.phone);
        loadingButton = findViewById(R.id.beli);
        myMessage = new MyMessage(this);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                produkModelList.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        listProduk = findViewById(R.id.listProduk);
        produkModelList = new ArrayList<>();
        loadingButton.setOnClickListener(v->{
            if (validate(phone,"Nomor tujuan pengisian tidak boleh kosong")){
                if (phone.getText().toString().length() < 6){
                    myMessage.Error("Masukan nomor tujuan yang sesuai");
                }else{
                    pilihProduk = new PilihProduk(this,produkModelList,phone.getText().toString().trim(),this,"prabayar");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    listProduk.setLayoutManager(linearLayoutManager);
                    listProduk.setAdapter(pilihProduk);
                    getProduct();
                    JavaClass.HideButton(this,this);
                }
            }
        });
    }
    void getProduct(){
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category","E-Money");
            jsonObject.put("operator",spinner.getSelectedItem());
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