package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.pemilu.DprDActivity;
import gens.global.gensmasterapps.pemilu.DprProvActivity;
import gens.global.gensmasterapps.pemilu.DprRIActivity;
import gens.global.gensmasterapps.pemilu.PresidenActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemiluActivity extends AppCompatActivity {
    Toolbar toolbar;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    MySession mySession;
    MyMessage myMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilu);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        mySession = new MySession(this);
        myMessage = new MyMessage(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pemilu 2024");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
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

    public void Pesiden(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code","presiden"+mySession.getPhone());
            defaultModelCall = apiService.CheckPresiden(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekcalon", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        if (response.body().getData().getStatus() == 1){
                            myMessage.Error("Data Quick Count Presiden Sudah ada dalam server");
                        }else{
                            Intent intent = new Intent(getApplicationContext(), PresidenActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), PresidenActivity.class);
                        startActivity(intent);
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

    public void DPRRI(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code","drpri"+mySession.getPhone());
            jsonObject.put("jenis","pusat");
            defaultModelCall = apiService.CheckCaleg(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekcalon", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        if (response.body().getData().getStatus() == 1){
                            myMessage.Error("Data Quick Count DPR RI Sudah ada dalam server");
                        }else{
                            Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                            intent.putExtra("jenis","pusat");
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                        intent.putExtra("jenis","pusat");
                        startActivity(intent);
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

    public void DPRPROV(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code","drprprov"+mySession.getPhone());
            jsonObject.put("jenis","provinsi");
            defaultModelCall = apiService.CheckCaleg(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekcalon", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        if (response.body().getData().getStatus() == 1){
                            myMessage.Error("Data Quick Count DPR Provinsi Sudah ada dalam server");
                        }else{
                            Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                            intent.putExtra("jenis","provinsi");
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                        intent.putExtra("jenis","provinsi");
                        startActivity(intent);
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

    public void DPRPD(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code","drprd"+mySession.getPhone());
            jsonObject.put("jenis","kabupaten");
            defaultModelCall = apiService.CheckCaleg(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("cekcalon", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        if (response.body().getData().getStatus() == 1){
                            myMessage.Error("Data Quick Count Kabupaten Sudah ada dalam server");
                        }else{
                            Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                            intent.putExtra("jenis","kabupaten");
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), DprRIActivity.class);
                        intent.putExtra("jenis","kabupaten");
                        startActivity(intent);
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
}