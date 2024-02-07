package gens.global.gensmasterapps.transfer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;


public class TransferBankFragment extends Fragment {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transfer_bank,
                container, false);
        autoCompleteTextView = root.findViewById(R.id.autoCompleteTextView);
        String jsonData = loadJSONFromAsset("bank.json");

        // Convert JSON data to a list of strings
        List<String> dataList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String bankName = jsonObject.getString("name")
                        +" - "+jsonObject.getString("code");
                dataList.add(bankName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line,
                dataList);
        autoCompleteTextView.setAdapter(adapter);
        return root;
    }
    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = requireContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}