package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.MitraAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.function.PopUp;
import gens.global.gensmasterapps.model.MitraModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MitraActivity extends AppCompatActivity {
    Toolbar toolbar;
    LoadingButton loadingButton;
    PopUp popUp;

    MitraAdapter mitraAdapter;
    List<MitraModel> mitraModelList;
    Call<List<MitraModel>> listCall;
    RecyclerView listMitra;
    ApiService apiService;
    MySession mySession;
    MyMessage myMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(this);
        mySession = new MySession(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List Mitra");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        loadingButton = findViewById(R.id.add_mitra);
        popUp = new PopUp(this,this);
        loadingButton.setOnClickListener(V->{
            popUp.TambahMitra();
        });
        listMitra = findViewById(R.id.listMitra);
        mitraModelList = new ArrayList<>();
        mitraAdapter = new MitraAdapter(this,mitraModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listMitra.setLayoutManager(linearLayoutManager);
        listMitra.setAdapter(mitraAdapter);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",mySession.getPhone());
            listCall = apiService.Downline(jsonObject.toString());
            listCall.enqueue(new Callback<List<MitraModel>>() {
                @Override
                public void onResponse(Call<List<MitraModel>> call, Response<List<MitraModel>> response) {
                    assert response.body() != null;
                    mitraModelList = response.body();
                    mitraAdapter.setMitra(mitraModelList);
                }

                @Override
                public void onFailure(Call<List<MitraModel>> call, Throwable t) {

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