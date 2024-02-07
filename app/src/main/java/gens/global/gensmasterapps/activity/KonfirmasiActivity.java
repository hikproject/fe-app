package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView invoice,date,produk,pelanggan,price,keterangan;
    Button topup;
    LoadingButton beli;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    MySession mySession;
    String kode_trx;
    MyMessage myMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        mySession = new MySession(this);
        myMessage = new MyMessage(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Konfirmasi");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        invoice = findViewById(R.id.invoice);
        date = findViewById(R.id.date);
        produk = findViewById(R.id.produk);
        pelanggan = findViewById(R.id.pelanggan);
        price = findViewById(R.id.price);
        keterangan = findViewById(R.id.keterangan);
        Date currentDate = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Jakarta");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(timeZone);
        produk.setText(Objects.requireNonNull(getIntent().getExtras()).getString("produk"));
        pelanggan.setText(getIntent().getExtras().getString("pelanggan"));
        price.setText(getIntent().getExtras().getString("price"));
        keterangan.setText(getIntent().getExtras().getString("desc"));
        switch (Objects.requireNonNull(getIntent().getExtras().getString("jenis"))){
            case "prabayar":
                kode_trx = "pra"+JavaClass.Invoice();
                break;
            case "pascabayar":
                kode_trx = "pasca"+JavaClass.Invoice();
                break;
        }
        invoice.setText(kode_trx);
        date.setText(dateFormat.format(currentDate));
        beli = findViewById(R.id.beli);
        topup = findViewById(R.id.topup);
        JSONObject json = new JSONObject();
        try {
            json.put("phone",mySession.getPhone());
            defaultModelCall = apiService.getUsers(json.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    int harga = JavaClass.number(Objects.requireNonNull(getIntent().getExtras().getString("price")));
                    if (harga > response.body().getData().getBalance()){
                        topup.setVisibility(View.VISIBLE);
                        topup.setOnClickListener(V->{
                            Intent intent = new Intent(getApplicationContext(),TopUpActivity.class);
                            startActivity(intent);
                        });
                    }else{
                        beli.setVisibility(View.VISIBLE);
                        beli.setOnClickListener(V->{
                            belipulsa();
                        });

                    }

                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    void belipulsa(){
        beli.showLoading();
        JSONObject json = new JSONObject();
        try {
            json.put("invoice",kode_trx);
            json.put("type",Objects.requireNonNull(getIntent().getExtras()).getString("jenis"));
            json.put("members",mySession.getPhone());
            json.put("produk",Objects.requireNonNull(getIntent().getExtras()).getString("produk"));
            json.put("keterangan", Objects.requireNonNull(getIntent().getExtras()).getString("desc"));
            json.put("pelanggan",Objects.requireNonNull(getIntent().getExtras()).getString("pelanggan"));
            defaultModelCall = apiService.BeliPulsa(json.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    beli.hideLoading();
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        Intent intent = new Intent(getApplicationContext(),InvoiceActivity.class);
                        intent.putExtra("invoice",kode_trx);
                        startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {

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