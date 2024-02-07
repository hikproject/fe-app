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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.model.MitraModel;

public class MitraAdapter extends RecyclerView.Adapter<MitraAdapter.MyViewHolder> {
    Context context;
    List<MitraModel> list_today;
    TimeZone timeZone;
    SimpleDateFormat sdf;
    @SuppressLint("SimpleDateFormat")
    public MitraAdapter(Context context, List<MitraModel> listSurat) {
        this.context = context;
        this.list_today = listSurat;
        timeZone = TimeZone.getTimeZone("Asia/Jakarta");
        TimeZone.setDefault(timeZone);
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setMitra(List<MitraModel> movieList) {
        this.list_today = movieList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_mitra,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(list_today.get(position).getName());
        holder.phone.setText(list_today.get(position).getPhone());



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
        TextView name,phone;
        LinearLayout list_report;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            list_report = itemView.findViewById(R.id.list);
        }
    }
}
