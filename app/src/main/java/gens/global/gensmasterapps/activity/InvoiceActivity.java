package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.model.InvoiceModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends AppCompatActivity {
    Toolbar toolbar;
    Call<InvoiceModel > invoiceModelCall;
    ApiService apiService;
    TextView invoice,tanggal,pelanggan,produk,keterangan,price,admin,komisi,status,sn;
    CardView card_sn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bukti Transaksi");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        invoice = findViewById(R.id.invoice);
        status = findViewById(R.id.status);
        tanggal = findViewById(R.id.tanggal);
        pelanggan = findViewById(R.id.pelanggan);
        produk = findViewById(R.id.produk);
        keterangan = findViewById(R.id.keterangan);
        price = findViewById(R.id.price);
        admin = findViewById(R.id.admin);
        komisi = findViewById(R.id.komisi);
        card_sn = findViewById(R.id.card_sn);
        sn = findViewById(R.id.sn);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("invoice",getIntent().getExtras().getString("invoice"));
            invoiceModelCall = apiService.Invoice(jsonObject.toString());
            invoiceModelCall.enqueue(new Callback<InvoiceModel>() {
                @Override
                public void onResponse(Call<InvoiceModel> call, Response<InvoiceModel> response) {
                    assert response.body() != null;
                    Log.d("Invoice", new Gson().toJson(response.body()));
                    invoice.setText(response.body().getData().getCode());
                    pelanggan.setText(response.body().getData().getPelanggan());
                    produk.setText(response.body().getData().getProduk());
                    keterangan.setText(response.body().getData().getKeterangan());
                    price.setText(JavaClass.Rupiah(Integer.parseInt(response.body().getData().getNominal())));
                    admin.setText(JavaClass.Rupiah(Integer.parseInt(response.body().getData().getAdmin())));
                    komisi.setText(JavaClass.Rupiah(Integer.parseInt(response.body().getData().getKomisi())));
                    Instant instant = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        instant = Instant.parse(response.body().getData().getCreated_at());
                        ZoneId zoneIdIndonesia = ZoneId.of("Asia/Jakarta");
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneIdIndonesia);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        String formattedDateTime = localDateTime.format(formatter);
                        tanggal.setText(formattedDateTime);
                    }
                    switch (response.body().getData().getStatus()){
                        case "0":
                            status.setText("Pending");
                            status.setTextColor(getResources().getColor(R.color.Primary));
                            break;
                        case "1":
                            status.setText("Sukses");
                            status.setTextColor(getResources().getColor(R.color.green));
                            card_sn.setVisibility(View.VISIBLE);
                            sn.setText(response.body().getData().getSn());
                            break;
                        case "2":
                            status.setText("Gagal");
                            status.setTextColor(getResources().getColor(R.color.red));
                            break;
                    }
                }

                @Override
                public void onFailure(Call<InvoiceModel> call, Throwable t) {
                    Log.d("Invoice", call.toString(),t);
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToHome();
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigateToHome();
        return true;
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}