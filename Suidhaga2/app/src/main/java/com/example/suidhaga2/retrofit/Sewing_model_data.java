package com.example.suidhaga2.retrofit;

import com.google.gson.annotations.SerializedName;

public class Sewing_model_data {

    @SerializedName("job_no")
    String job_no;

    @SerializedName("buyer")
    String buyer;

    @SerializedName("style")
    String style;

    @SerializedName("production_qty")
    String production_qty;

    @SerializedName("fabric")
    String fabric;

    @SerializedName("color")
    String color;

    @SerializedName("buyer_po")
    String buyer_po;

    @SerializedName("order_qty")
    String order_qty;

    @SerializedName("total_issue")
    String total_issue;

    @SerializedName("total_received")
    String total_received;

    @SerializedName("balance")
    String balance;

    @SerializedName("last_updated")
    String Date;

    public Sewing_model_data(String job_no, String buyer, String style, String production_qty, String fabric, String color, String buyer_po, String order_qty, String total_issue, String total_received, String balance,String Date) {
        this.job_no = job_no;
        this.buyer = buyer;
        this.style = style;
        this.production_qty = production_qty;
        this.fabric = fabric;
        this.color = color;
        this.buyer_po = buyer_po;
        this.order_qty = order_qty;
        this.total_issue = total_issue;
        this.total_received = total_received;
        this.balance = balance;
        this.Date=Date;
    }

    public String getJob_no() {
        return job_no;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getStyle() {
        return style;
    }

    public String getProduction_qty() {
        return production_qty;
    }

    public String getFabric() {
        return fabric;
    }

    public String getColor() {
        return color;
    }

    public String getBuyer_po() {
        return buyer_po;
    }

    public String getOrder_qty() {
        return order_qty;
    }

    public String getTotal_issue() {
        return total_issue;
    }

    public String getTotal_received() {
        return total_received;
    }

    public String getBalance() {
        return balance;
    }

    public String getDate(){
        return Date;
    }
}
