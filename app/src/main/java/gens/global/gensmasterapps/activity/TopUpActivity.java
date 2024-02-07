package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUpActivity extends AppCompatActivity {

    MyMessage myMessage;
    MySession mySession;
    EditText amount;
    LoadingButton loadingButton;
    Toolbar toolbar;
    Call<Category> categoryCall;
    ApiService apiService;
    String invoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(this);
        mySession = new MySession(this);
        amount = findViewById(R.id.amount);
        loadingButton = findViewById(R.id.payment);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("TopUp Saldo");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        invoice = "sld"+JavaClass.Invoice();
        loadingButton.setOnClickListener(V->{
            if (JavaClass.Validate(amount,"Jumlah TopUp Tidak boleh kosong",this)){
                int jumlah = Integer.valueOf(amount.getText().toString().trim());
                if (jumlah < 49999){
                    myMessage.Error("Minimal TopUp Rp. 50.0000");
                }else{
                    int total = jumlah + Integer.parseInt(JavaClass.KoduUnik());
                    popup(total);

                }
            }

        });

    }
    private void TopUpAct(int nominal){
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("invoice",invoice);
            jsonObject.put("type","topup");
            jsonObject.put("pelanggan",mySession.getPhone());
            jsonObject.put("members",mySession.getPhone());
            jsonObject.put("produk","TopUp Saldo");
            jsonObject.put("keterangan","TopUp Saldo Gen's Pay");
            jsonObject.put("nominal",nominal);
            jsonObject.put("admin",0);
            jsonObject.put("komisi",0);
            jsonObject.put("status",0);
            categoryCall = apiService.TopUpSaldo(jsonObject.toString());
            categoryCall.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    assert response.body() != null;
                    loadingButton.hideLoading();
                    Log.d("saveTopUp", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        amount.setText("");
                        Intent intent = new Intent(getApplicationContext(),InvoiceActivity.class);
                        intent.putExtra("invoice",invoice);
                        startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<Category> call, Throwable t) {
                    Log.d("saveTopUp", t.getMessage());
                }
            });
        } catch (JSONException e) {
            Log.d("saveTopUp", e.getMessage());
            throw new RuntimeException(e);
        }

    }
    void popup(int nominal){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.topup, null, false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
        TextView nominals = view.findViewById(R.id.nominal);
        TextView norek = view.findViewById(R.id.norek);
        norek.setOnClickListener(V->{
            String nomorRekening = "035501002483306";
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Nomor Rekening", nomorRekening);
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipData);
                myMessage.Success("Nomor rekening berhasil disalin");
            }
        });
        nominals.setText(JavaClass.Rupiah(nominal));
        LoadingButton loadingButtons = view.findViewById(R.id.daftar);
        loadingButtons.setOnClickListener(V->{
            TopUpAct(nominal);
            dialog.dismiss();
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