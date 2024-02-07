package gens.global.gensmasterapps.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.WebViewActivity;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {
    MySession mySession;
    TextView logout;
    TextView name,phone,saldo;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    TextView about,term,privacy,support,mutasi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        mySession = new MySession(requireContext());
        logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(V->{
            mySession.logout();
        });
        name = root.findViewById(R.id.name);
        phone = root.findViewById(R.id.phone);
        saldo = root.findViewById(R.id.saldo);
        name.setText(mySession.getName());
        phone.setText(mySession.getPhone());
        JSONObject json = new JSONObject();
        try {
            json.put("phone",mySession.getPhone());
            defaultModelCall = apiService.getUsers(json.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("getUsers", new Gson().toJson(response.body()));
                    saldo.setText(JavaClass.Rupiah(response.body().getData().getBalance()));
                }

                @Override
                public void onFailure(Call<DefaultModel> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        about = root.findViewById(R.id.about);
        term = root.findViewById(R.id.term);
        privacy = root.findViewById(R.id.privacy);
        support = root.findViewById(R.id.support);
        mutasi = root.findViewById(R.id.mutasi);
        about.setOnClickListener(V->{
            Intent intent = new Intent(requireContext(), WebViewActivity.class);
            intent.putExtra("title","Tentang Aplikasi");
            intent.putExtra("html","about.html");
            startActivity(intent);
        });
        term.setOnClickListener(V->{
            Intent intent = new Intent(requireContext(), WebViewActivity.class);
            intent.putExtra("title","Syarat dan Ketentuan");
            intent.putExtra("html","term.html");
            startActivity(intent);
        });
        privacy.setOnClickListener(V->{
            Intent intent = new Intent(requireContext(), WebViewActivity.class);
            intent.putExtra("title","Kebijakan Privasi");
            intent.putExtra("html","privacy.html");
            startActivity(intent);
        });
        support.setOnClickListener(V->{
            Intent intent = new Intent(requireContext(), WebViewActivity.class);
            intent.putExtra("title","Informasi dan Bantuan");
            intent.putExtra("html","info.html");
            startActivity(intent);
        });
        return root;
    }
}