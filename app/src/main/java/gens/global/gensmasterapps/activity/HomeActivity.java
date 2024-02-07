package gens.global.gensmasterapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URISyntaxException;
import java.util.List;

import gens.global.gensmasterapps.R;

import gens.global.gensmasterapps.function.NotificationHelper;
import gens.global.gensmasterapps.menu.AccountFragment;
import gens.global.gensmasterapps.menu.HomeFragment;
import gens.global.gensmasterapps.menu.PriceFragment;
import gens.global.gensmasterapps.menu.TransactionFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.menu_bottom);
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.menu_home) {
            fragment = new HomeFragment();
        } else if (item.getItemId() == R.id.menu_harga) {
            fragment = new PriceFragment();
        } else if (item.getItemId() == R.id.menu_transaksi) {
            fragment = new TransactionFragment();
        } else if (item.getItemId() == R.id.menu_akun) {
            fragment = new AccountFragment();
        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        // switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_containers, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}