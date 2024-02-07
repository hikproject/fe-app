package gens.global.gensmasterapps.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.function.Product;


public class TransaksiPascaFragment extends Fragment {
    Toolbar toolbar;
    Spinner spinner;
    Product product;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaksi_pasca, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Bundle arguments = getArguments();
        toolbar = root.findViewById(R.id.toolbar);
        assert arguments != null;
        toolbar.setTitle(arguments.getString("category"));
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        spinner = root.findViewById(R.id.spinner);
        product = new Product(requireContext());
        product.checkTagihan(spinner,arguments.getString("category"));
        return root;
    }
}