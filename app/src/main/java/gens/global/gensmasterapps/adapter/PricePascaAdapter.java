package gens.global.gensmasterapps.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.function.JavaClass;
import gens.global.gensmasterapps.model.ProdukModel;

public class PricePascaAdapter extends RecyclerView.Adapter<PricePascaAdapter.MyViewHolder>{
    List<ProdukModel> produkModelList;
    Context context;
    public PricePascaAdapter (Context context, List<ProdukModel> produkModelList){
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
        View itemView = inflater.inflate(R.layout.list_harga_pasca,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(produkModelList.get(position).getName());
        holder.desc.setText(produkModelList.get(position).getDesc());
        holder.admin.setText(JavaClass.Rupiah(Integer.parseInt(produkModelList.get(position).getAdmin())));
        if (Integer.parseInt(produkModelList.get(position).getKomisi()) > 0 && Integer.parseInt(produkModelList.get(position).getKomisi()) > 250){
            int komisi = Integer.parseInt(produkModelList.get(position).getKomisi()) - 250;
            holder.komisi.setText("Komisi : "+JavaClass.Rupiah(komisi));
        }else{
            holder.komisi.setText("Komisi : "+JavaClass.Rupiah(Integer.parseInt(produkModelList.get(position).getKomisi())));
        }

    }

    @Override
    public int getItemCount() {
        if(produkModelList != null){
            return produkModelList.size();
        }
        return 0;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,desc,admin,komisi;
        LinearLayout list_report;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            admin = itemView.findViewById(R.id.admin);
            komisi = itemView.findViewById(R.id.komisi);
            list_report = itemView.findViewById(R.id.list);
        }
    }
}
