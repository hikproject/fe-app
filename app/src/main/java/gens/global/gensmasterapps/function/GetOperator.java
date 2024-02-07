package gens.global.gensmasterapps.function;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.model.Item;

public class GetOperator {
    private static final String TAG = "GetOperator";
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
    Context mContext;
    String nama_operator;
    public GetOperator(Context context){
        items = new ArrayList<>();
        this.mContext = context;
    }
    public String showOperator(String searchTerm){
        try {
            int rawResourceId = R.raw.prefix;
            InputStream inputStream = mContext.getResources().openRawResource(rawResourceId);

            // Read the JSON content
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonContent = new String(buffer, StandardCharsets.UTF_8);
            // Parse JSON to a list of items
            Type itemType = new TypeToken<List<Item>>() {}.getType();
            items = new Gson().fromJson(jsonContent, itemType);

            // Search for items
            List<Item> matchingItems = new ArrayList<>();
            for (Item item : items) {
                if (item.getPrefix().equals(searchTerm)) {
                    matchingItems.add(item);
                }
            }

            // Handle matching items
            if (!matchingItems.isEmpty()) {
                for (Item matchingItem : matchingItems) {
                    nama_operator = toTitleCase(matchingItem.getNama());
                    Log.d(TAG, "Found item: " + matchingItem.getNama());
                }
            } else {
                Log.d(TAG, "No matching items found.");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading JSON file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "Error parsing JSON: " + e.getMessage());
        }
        return nama_operator;
    }
    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split("\\s+");
        StringBuilder titleCase = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                titleCase.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
            }
        }
        return titleCase.toString().trim();
    }
}
