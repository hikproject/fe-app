package gens.global.gensmasterapps.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.AuthActivity;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    TextView masuk;
    EditText phone,name,pin,nik,upline;
    LoadingButton daftar;
    MyMessage myMessage;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(getContext());
        masuk = root.findViewById(R.id.masuk);
        phone = root.findViewById(R.id.phone);
        name = root.findViewById(R.id.name);
        nik = root.findViewById(R.id.nik);
        pin = root.findViewById(R.id.pin);
        upline = root.findViewById(R.id.upline);
        daftar = root.findViewById(R.id.daftar);
        masuk.setOnClickListener(V->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        daftar.setOnClickListener(V->{
            if (validate(phone,"Nomor Handphone tidak boleh kodong")
                    && validate(name,"Nama Lengkap tidak boleh kodong")
                    && validate(nik,"No Identitas tidak boleh kodong")
                    && validate(pin,"PIN Transaksi tidak boleh kodong")){
                registered();
            }

        });
        return root;
    }
    private void registered(){
        daftar.showLoading();
        JSONObject obj = new JSONObject();
        try {
            obj.put("phone", phone.getText().toString().trim());
            obj.put("pin", pin.getText().toString().trim());
            obj.put("name", name.getText().toString().trim());
            obj.put("nik", nik.getText().toString().trim());
            obj.put("upline", upline.getText().toString().trim());
            defaultModelCall = apiService.signupUser(obj.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("registerUsersSuccess", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        Intent intent = new Intent(requireContext(), AuthActivity.class);
                        requireActivity().startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                    daftar.hideLoading();
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {

                }
            });
        }catch (JSONException e) {
            Log.d("LoginUsersError", Objects.requireNonNull(e.getMessage()));
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