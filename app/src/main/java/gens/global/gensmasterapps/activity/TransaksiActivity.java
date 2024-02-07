package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.google.gson.Gson;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.fragment.AuthFragment;
import gens.global.gensmasterapps.fragment.TransaksiPascaFragment;
import gens.global.gensmasterapps.fragment.TransaksiPrabayarFragment;

public class TransaksiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        Bundle bundle = new Bundle();
        bundle.putString("category", getIntent().getExtras().getString("category"));

        TransaksiPrabayarFragment transaksiPrabayarFragment = new TransaksiPrabayarFragment();
        transaksiPrabayarFragment.setArguments(bundle);

        TransaksiPascaFragment transaksiPascaFragment = new TransaksiPascaFragment();
        transaksiPascaFragment.setArguments(bundle);

        if (getIntent().getExtras().getInt("type") == 1){
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, transaksiPrabayarFragment)
                        .commit();
            }
        }else{
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, transaksiPascaFragment)
                        .commit();
            }
        }
    }
}