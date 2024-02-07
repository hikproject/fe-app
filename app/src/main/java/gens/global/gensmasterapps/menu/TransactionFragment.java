package gens.global.gensmasterapps.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.TransaksiAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.TransaksiModel;
import gens.global.gensmasterapps.model.TrxModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionFragment extends Fragment {
    List<TransaksiModel> transaksiModels;
    TransaksiAdapter transaksiAdapter;
    RecyclerView listTransaction;
    ApiService apiService;
    Call<TrxModel> trxModelCall;
    MySession mySession;
    TextView empty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);
        mySession = new MySession(requireContext());
        apiService = RetrofitClient.getClient().create(ApiService.class);
        empty = root.findViewById(R.id.empty);
        listTransaction = root.findViewById(R.id.listTransaction);
        transaksiModels = new ArrayList<>();
        transaksiAdapter = new TransaksiAdapter(requireContext(), transaksiModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        listTransaction.setLayoutManager(linearLayoutManager);
        listTransaction.setAdapter(transaksiAdapter);
        getData();
        return root;
    }
    private void getData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",mySession.getPhone());
            trxModelCall = apiService.getTransaction(jsonObject.toString());
            trxModelCall.enqueue(new Callback<TrxModel>() {
                @Override
                public void onResponse(Call<TrxModel> call, Response<TrxModel> response) {
                    if (response.body().getData().size() == 0){
                        empty.setVisibility(View.VISIBLE);
                        listTransaction.setVisibility(View.GONE);
                    }else{
                        transaksiModels = response.body().getData();
                        transaksiAdapter.setTransaksi(transaksiModels);
                    }

                }
                @Override
                public void onFailure(Call<TrxModel> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}