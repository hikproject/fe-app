package gens.global.gensmasterapps.model;

import com.google.gson.annotations.SerializedName;

public class UsersData {
    @SerializedName("phone")
    private String phone;
    @SerializedName("name")
    private String name;
    @SerializedName("balance")
    private int balance;
    @SerializedName("status")
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @SerializedName("subscriber_id")
    private String subscriber_id;
    @SerializedName("segment_power")
    private String segment_power;

    public String getSegment_power() {
        return segment_power;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSegment_power(String segment_power) {
        this.segment_power = segment_power;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
