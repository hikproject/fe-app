package gens.global.gensmasterapps.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.droidbond.loadingbutton.LoadingButton;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.model.Category;
import retrofit2.Call;


public class BpjsKesehatanFragment extends Fragment {
    EditText phone;
    LoadingButton loadingButton;
    Call<Category> categoryCall;
    ApiService apiService;
    MyMessage myMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bpjs_kesehatan, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        phone = root.findViewById(R.id.phone);
        loadingButton = root.findViewById(R.id.beli);
        loadingButton.setOnClickListener(V->{
            if (validate(phone,"No. Pelanggan tidak boleh kosong")){
                cekTagihan();
            }
        });
        return root;
    }
    void cekTagihan(){

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