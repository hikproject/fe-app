package gens.global.gensmasterapps.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.MitraActivity;
import gens.global.gensmasterapps.activity.PemiluActivity;
import gens.global.gensmasterapps.activity.TopUpActivity;
import gens.global.gensmasterapps.activity.TransferActivity;
import gens.global.gensmasterapps.adapter.SliderAdapter;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.fragment.FragmentSlider;
import gens.global.gensmasterapps.function.BannerSlider;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.function.SliderIndicator;
import gens.global.gensmasterapps.model.Category;
import gens.global.gensmasterapps.model.DefaultModel;
import gens.global.gensmasterapps.pembayaran.BpjsActivity;
import gens.global.gensmasterapps.pembayaran.DataActivity;
import gens.global.gensmasterapps.pembayaran.LainnyaActivity;
import gens.global.gensmasterapps.pembayaran.MoneyActivity;
import gens.global.gensmasterapps.pembayaran.PlnActivity;
import gens.global.gensmasterapps.pembayaran.PulsaActivity;
import gens.global.gensmasterapps.pembayaran.TagihanActivity;
import gens.global.gensmasterapps.pembayaran.TvActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    BannerSlider bannerSlider;
    List<Fragment> fragments;
    SliderAdapter sliderAdapter;
    SliderIndicator sliderIndicator;
    LinearLayout pagesContainer,pulsa,pln,data,money,bpjs,tv,tagihan,lainnya;
    Intent intent;
    TextView name,saldo;
    MySession mySession;
    LinearLayout topup,mitra,transfer,pemilu;
    Call<DefaultModel> defaultModelCall;
    ApiService apiService;
    TextView greating;
    VideoView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        bannerSlider = root.findViewById(R.id.sliderView);
        pagesContainer = root.findViewById(R.id.pagesContainer);
        greating = root.findViewById(R.id.greating);

        defaultModelCall = apiService.broadcast();
        defaultModelCall.enqueue(new Callback<DefaultModel>() {
            @Override
            public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                assert response.body() != null;
                greating.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<DefaultModel> call, Throwable t) {

            }
        });
        greating.setSelected(true);

        videoView = root.findViewById(R.id.videoview);
        String videoUrl = JavaClass.BaseUrl()+"video";
        Uri videoUri = Uri.parse(videoUrl);
        videoView.setVideoURI(videoUri);
        videoView.start();

        pulsa = root.findViewById(R.id.pulsa);
        pln = root.findViewById(R.id.pln);
        data = root.findViewById(R.id.data);
        money = root.findViewById(R.id.money);
        bpjs = root.findViewById(R.id.bpjs);
        tv = root.findViewById(R.id.tv);
        tagihan = root.findViewById(R.id.tagihan);
        lainnya = root.findViewById(R.id.lainnya);
        bannerSlider.setDurationScroll(800);
        fragments = new ArrayList<>();
        showSlider();
        pemilu = root.findViewById(R.id.pemilu);
        pemilu.setOnClickListener(V->{
            intent = new Intent(requireContext(), PemiluActivity.class);
            startActivity(intent);
        });
        pulsa.setOnClickListener(V->{
            intent = new Intent(requireContext(), PulsaActivity.class);
            startActivity(intent);
        });
        pln.setOnClickListener(V->{
            intent = new Intent(requireContext(), PlnActivity.class);
            startActivity(intent);
        });
        data.setOnClickListener(V->{
            intent = new Intent(requireContext(), DataActivity.class);
            startActivity(intent);
        });
        money.setOnClickListener(V->{
            intent = new Intent(requireContext(), MoneyActivity.class);
            startActivity(intent);
        });
        bpjs.setOnClickListener(V->{
            intent = new Intent(requireContext(), BpjsActivity.class);
            startActivity(intent);
        });
        tv.setOnClickListener(V->{
            intent = new Intent(requireContext(), TvActivity.class);
            startActivity(intent);
        });
        tagihan.setOnClickListener(V->{
            intent = new Intent(requireContext(), TagihanActivity.class);
            startActivity(intent);
        });
        lainnya.setOnClickListener(V->{
            intent = new Intent(requireContext(), LainnyaActivity.class);
            startActivity(intent);
        });
        name = root.findViewById(R.id.name);
        saldo = root.findViewById(R.id.saldo);
        mySession = new MySession(requireContext());
        name.setText(mySession.getName());
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
            Log.d("getUsers", e.getMessage());
        }

        topup = root.findViewById(R.id.topup);
        mitra = root.findViewById(R.id.mitra);
        transfer = root.findViewById(R.id.transfer);
        topup.setOnClickListener(V->{
            intent = new Intent(requireContext(), TopUpActivity.class);
            startActivity(intent);
        });
        mitra.setOnClickListener(V->{
            intent = new Intent(requireContext(), MitraActivity.class);
            startActivity(intent);
        });
        transfer.setOnClickListener(V->{
            intent = new Intent(requireContext(), TransferActivity.class);
            startActivity(intent);
        });
        return root;
    }

    void showSlider(){
        fragments.add(FragmentSlider.newInstance(JavaClass.BaseUrl()+"banner/slide_1.jpg"));
        fragments.add(FragmentSlider.newInstance(JavaClass.BaseUrl()+"banner/slide_2.jpg"));
        fragments.add(FragmentSlider.newInstance(JavaClass.BaseUrl()+"banner/slide_3.jpg"));
        sliderAdapter = new SliderAdapter(requireActivity().getSupportFragmentManager(), fragments);
        bannerSlider.setAdapter(sliderAdapter);
        sliderIndicator = new SliderIndicator(requireContext(), pagesContainer, bannerSlider,
                R.drawable.indicator_circle);
        sliderIndicator.setPageCount(fragments.size());
        sliderIndicator.show();
    }
}