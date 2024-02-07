package gens.global.gensmasterapps.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.net.URISyntaxException;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.fragment.AuthFragment;


public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AuthFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}