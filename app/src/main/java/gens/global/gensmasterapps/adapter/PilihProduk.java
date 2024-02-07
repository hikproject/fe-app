package gens.global.gensmasterapps.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import gens.global.gensmasterapps.function.Notify;
import gens.global.gensmasterapps.model.ProdukModel;

public class PilihProduk extends RecyclerView.Adapter<PilihProduk.MyViewHolder>{
    List<ProdukModel> produkModelList;
    Context context;
    String pelanggan;
    Notify notify;
    Activity mActivity;
    String jenis;
    public PilihProduk (Context context, List<ProdukModel> produkModelList,String pelanggan,Activity activity,String jenis){
        this.context = context;
        this.mActivity = activity;
        this.pelanggan = pelanggan;
        this.jenis = jenis;
        this.produkModelList = produkModelList;
        notify = new Notify(context,mActivity);
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
        View itemView = inflater.inflate(R.layout.list_harga,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(produkModelList.get(position).getName());
        holder.desc.setText(produkModelList.get(position).getDesc());
        holder.price.setText(JavaClass.Rupiah(Integer.valueOf(produkModelList.get(position).getSell())));
        holder.list.setOnClickListener(V->{
            notify.KonfirmasiPulsa(pelanggan,
                    produkModelList.get(position).getName(),
                    JavaClass.Rupiah(Integer.parseInt(produkModelList.get(position).getSell())),
                    produkModelList.get(position).getDesc(),
                    jenis
                    );
        });
    }

    @Override
    public int getItemCount() {
        if(produkModelList != null){
            return produkModelList.size();
        }
        return 0;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,desc,price;
        LinearLayout list;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            price = itemView.findViewById(R.id.price);
            list = itemView.findViewById(R.id.list);
        }
    }
}
