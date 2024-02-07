package gens.global.gensmasterapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdukModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sell")
    @Expose
    private String sell;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("komisi")
    @Expose
    private String komisi;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand")
    @Expose
    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdmin() {
        return admin;
    }

    public String getKomisi() {
        return komisi;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getSell() {
        return sell;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
