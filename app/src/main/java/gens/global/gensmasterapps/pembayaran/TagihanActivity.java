package gens.global.gensmasterapps.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.function.Product;

public class TagihanActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    Product product;
    LoadingButton loadingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pembayaran Tagihan");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        spinner = findViewById(R.id.spinner);
        product = new Product(this);
        product.Tagihan(spinner);
        loadingButton = findViewById(R.id.beli);
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