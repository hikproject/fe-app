package gens.global.gensmasterapps.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import gens.global.gensmasterapps.activity.HomeActivity;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthFragment extends Fragment {
    TextView daftar;
    EditText phone,pin;
    LoadingButton login;
    MyMessage myMessage;
    MySession mySession;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auth, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(getContext());
        mySession = new MySession(requireContext());
        daftar = root.findViewById(R.id.daftar);
        phone = root.findViewById(R.id.phone);
        pin = root.findViewById(R.id.pin);
        login = root.findViewById(R.id.login);

        daftar.setOnClickListener(V-> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .addToBackStack(null)
                .commit());

        login.setOnClickListener(V->{
            if (validate(phone,"No, Handphone tidak boleh kosong") && validate(pin,"PIN Transaksi tidak boleh kosong")) {

                startLogin();

            }

        });

        return root;
    }
    private void startLogin(){
        login.showLoading();
        JSONObject obj = new JSONObject();
        try {
            obj.put("phone", phone.getText().toString().trim());
            obj.put("pin", pin.getText().toString().trim());
            defaultModelCall = apiService.loginUser(obj.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("LoginUsersSuccess", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        mySession.createSession(response.body().getData().getPhone(),response.body().getData().getName());
                        Intent intent = new Intent(requireContext(), HomeActivity.class);
                        requireActivity().startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                    login.hideLoading();
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {
                    Log.d("LoginUsersError", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (JSONException e) {
            Log.d("LoginUsersError", Objects.requireNonNull(e.getMessage()));
            throw new RuntimeException(e);
        }
    }
    boolean validate(EditText editText, String pesan) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        myMessage.Error(pesan);
        editText.requestFocus();
        return false;
    }
}