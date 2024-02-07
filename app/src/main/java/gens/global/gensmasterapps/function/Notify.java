package gens.global.gensmasterapps.function;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import gens.global.gensmasterapps.activity.KonfirmasiActivity;
import gens.global.gensmasterapps.R;

public class Notify {
    TextView pelanggan,produk,keterangan,harga;
    Activity mActivity;
    Button next,cancel;
    Context mContext;
    public Notify(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }
    public void KonfirmasiPulsa(String customer,String product,String price,String desc,String jenis){
        Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.konfirmasi_pulsa, null, false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        pelanggan = view.findViewById(R.id.pelanggan);
        produk = view.findViewById(R.id.produk);
        harga = view.findViewById(R.id.harga);
        keterangan = view.findViewById(R.id.keterangan);
        next = view.findViewById(R.id.next);
        cancel = view.findViewById(R.id.cancle);
        pelanggan.setText(customer);
        produk.setText(product);
        harga.setText(price);
        keterangan.setText(desc);
        next.setOnClickListener(View->{
            Intent intent = new Intent(mContext, KonfirmasiActivity.class);
            intent.putExtra("jenis",jenis);
            intent.putExtra("produk",product);
            intent.putExtra("price",price);
            intent.putExtra("desc",desc);
            intent.putExtra("pelanggan",customer);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);
            dialog.dismiss();
        });
        dialog.show();
        cancel.setOnClickListener(View->{
            dialog.dismiss();
        });
    }
}
