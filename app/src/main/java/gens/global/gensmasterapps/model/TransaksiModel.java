package gens.global.gensmasterapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("produk")
    @Expose
    private String produk;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("nominal")
    @Expose
    private String nominal;
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("komisi")
    @Expose
    private String komisi;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("pelanggan")
    @Expose
    private String pelanggan;
    @SerializedName("sn")
    @Expose
    private String sn;

    public String getPelanggan() {
        return pelanggan;
    }

    public String getSn() {
        return sn;
    }

    public void setPelanggan(String pelanggan) {
        this.pelanggan = pelanggan;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public String getKomisi() {
        return komisi;
    }

    public String getNominal() {
        return nominal;
    }

    public String getStatus() {
        return status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getProduk() {
        return produk;
    }

    public String getType() {
        return type;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }
}
