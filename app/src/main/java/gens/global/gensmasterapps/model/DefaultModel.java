package gens.global.gensmasterapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class DefaultModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UsersData data;

    public String getCode() {
        return code;
    }

    public UsersData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(UsersData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
