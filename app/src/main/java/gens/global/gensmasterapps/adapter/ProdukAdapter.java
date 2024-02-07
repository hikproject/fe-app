package gens.global.gensmasterapps.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.DetailProdukActivity;
import gens.global.gensmasterapps.model.MitraModel;
import gens.global.gensmasterapps.model.ProdukModel;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder>{
    List<ProdukModel> produkModelList;
    Context context;
    public ProdukAdapter(Context context, List<ProdukModel> produkModelList){
        this.context = context;
        this.produkModelList = produkModelList;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setProduk(List<ProdukModel> produkModelList) {
        this.produkModelList = produkModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_produk,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.brand.setText(produkModelList.get(position).getCategory());
        holder.list_report.setOnClickListener(V->{
            Intent intent = new Intent(context, DetailProdukActivity.class);
            if (isAllUpperCase(produkModelList.get(position).getCategory().replace(" ",""))){

                if (produkModelList.get(position).getName().matches("PLN") || produkModelList.get(position).getName().matches("TV")){
                    intent.putExtra("type",1);
                }else{
                    intent.putExtra("type",2);
                }
            }else{
                intent.putExtra("type",1);
            }
            intent.putExtra("category",produkModelList.get(position).getCategory());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }
    public static boolean isAllUpperCase(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isUpperCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int getItemCount() {
        if(produkModelList != null){
            return produkModelList.size();
        }
        return 0;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView brand;
        LinearLayout list_report;
        public MyViewHolder(View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brand);
            list_report = itemView.findViewById(R.id.list);
        }
    }
}
