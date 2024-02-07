package gens.global.gensmasterapps.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.InvoiceActivity;
import gens.global.gensmasterapps.model.MitraModel;
import gens.global.gensmasterapps.model.TransaksiModel;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.MyViewHolder> {
    Context context;
    List<TransaksiModel> list_today;
    TimeZone timeZone;
    SimpleDateFormat sdf;
    @SuppressLint("SimpleDateFormat")
    public TransaksiAdapter(Context context, List<TransaksiModel> listSurat) {
        this.context = context;
        this.list_today = listSurat;
        timeZone = TimeZone.getTimeZone("Asia/Jakarta");
        TimeZone.setDefault(timeZone);
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setTransaksi(List<TransaksiModel> movieList) {
        this.list_today = movieList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_transaksi,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (list_today.get(position).getProduk().length() > 25){
            holder.produk.setText(list_today.get(position).getProduk().substring(0,25)+"...");
        }else{
            holder.produk.setText(list_today.get(position).getProduk());
        }
        if (list_today.get(position).getKeterangan().length() > 25){
            holder.keterangan.setText(list_today.get(position).getKeterangan().substring(0,25)+"...");
        }else{
            holder.keterangan.setText(list_today.get(position).getKeterangan());
        }
        switch (list_today.get(position).getStatus()){
            case "0":
                holder.status.setText("Pending");
                holder.status.setTextColor(context.getResources().getColor(R.color.Primary));
                break;
            case "1":
                holder.status.setText("Sukses");
                holder.status.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case "2":
                holder.status.setText("Gagal");
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }

        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = Instant.parse(list_today.get(position).getCreated_at());
            ZoneId zoneIdIndonesia = ZoneId.of("Asia/Jakarta");
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneIdIndonesia);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);
            holder.tanggal.setText(formattedDateTime);
        }
        holder.list_report.setOnClickListener(V->{
            Intent intent = new Intent(context, InvoiceActivity.class);
            intent.putExtra("invoice",list_today.get(position).getCode());
            context.startActivity(intent);
        });
    }
    public String Rupiah(int angka){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(angka);
        return yourFormattedString.replace(",", ".");
    }
    @Override
    public int getItemCount() {
        if(list_today != null){
            return list_today.size();
        }
        return 0;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView produk,keterangan,status,tanggal;
        LinearLayout list_report;
        public MyViewHolder(View itemView) {
            super(itemView);
            produk = itemView.findViewById(R.id.produk);
            keterangan = itemView.findViewById(R.id.keterangan);
            status = itemView.findViewById(R.id.status);
            tanggal = itemView.findViewById(R.id.tanggal);
            list_report = itemView.findViewById(R.id.list);
        }
    }
}
