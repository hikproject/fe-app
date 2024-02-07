package gens.global.gensmasterapps.function;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.MitraAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.model.MitraModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUp {
    Activity mActivity;
    Context mContext;
    EditText phone,name,pin,nik,upline;
    LoadingButton loadingButton;
    MyMessage myMessage;
    MySession mySession;
    Call<DefaultModel> defaultModelCall;
    Call<List<MitraModel>> listCall;
    ApiService apiService;
    List<MitraModel> mitraModelList;
    MitraAdapter mitraAdapter;
    public PopUp(Context context,Activity activity){
        this.mActivity = activity;
        this.mContext = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(mContext);
        mySession = new MySession(mContext);
        mitraModelList = new ArrayList<>();
        mitraAdapter = new MitraAdapter(mContext,mitraModelList);
    }
    public void TambahMitra(){
        Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tambah_mitra, null, false);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);
        nik = view.findViewById(R.id.nik);
        pin = view.findViewById(R.id.pin);
        upline = view.findViewById(R.id.upline);
        upline.setText(mySession.getPhone());
        upline.setEnabled(false);
        loadingButton = view.findViewById(R.id.daftar);
        loadingButton.setOnClickListener(V->{
            if (validate(phone,"Nomor Handphone tidak boleh kodong")
                    && validate(name,"Nama Lengkap tidak boleh kodong")
                    && validate(nik,"No Identitas tidak boleh kodong")
                    && validate(pin,"PIN Transaksi tidak boleh kodong")){
                AddClient(name.getText().toString().trim(),
                        phone.getText().toString().trim(),
                        nik.getText().toString().trim(),
                        pin.getText().toString().trim(),
                        upline.getText().toString().trim(),
                        dialog);

            }

        });
    }
    void AddClient(String nama,String hp,String nik,String pin,String upline,Dialog dialog){
        loadingButton.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",nama);
            jsonObject.put("phone",hp);
            jsonObject.put("nik",nik);
            jsonObject.put("pin",pin);
            jsonObject.put("upline",upline);
            defaultModelCall = apiService.signupUser(jsonObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        dialog.dismiss();
                        showDownline();
                    }else {
                        myMessage.Error(response.body().getMessage());
                    }
                    loadingButton.hideLoading();
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void showDownline(){
        mitraModelList.clear();
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
    public boolean validate(EditText editText, String pesan) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        myMessage.Error(pesan);
        editText.requestFocus();
        return false;
    }
}
