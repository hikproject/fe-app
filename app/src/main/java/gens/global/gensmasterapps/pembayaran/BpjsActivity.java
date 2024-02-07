package gens.global.gensmasterapps.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.adapter.TabAdapterBPJS;
import gens.global.gensmasterapps.adapter.TransferAdapter;

public class BpjsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapterBPJS tabAdapterBPJS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpjs);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi BPJS");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        viewPager = findViewById(R.id.viewPagers_bpjs);
        tabLayout = findViewById(R.id.tabs_bpjs);
        tabAdapterBPJS = new TabAdapterBPJS(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapterBPJS);
        tabLayout.setupWithViewPager(viewPager);
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