package com.example.suidhaga2.retrofit;

import com.google.gson.annotations.SerializedName;

public class Frr_data_model_data {
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

    @SerializedName("cut_avg")
    String Cutting_ord_avg;

    @SerializedName("actual_avg")
    String Actual_prod_avg;


    @SerializedName("actual_cut")
    String Actual_cutting;


    @SerializedName("fabric_issued")
    String Fabric_issued;

    @SerializedName("fabric_returned")
    String  Fabric_returned;

    @SerializedName("fabric_consumed")
   String Fabric_consumed;


    @SerializedName("balance")
   String Balance;

    @SerializedName("util")
   String util;


    public Frr_data_model_data(String job_no, String buyer, String style, String production_qty, String fabric, String color, String buyer_po, String order_qty, String cutting_ord_avg, String actual_prod_avg, String actual_cutting, String fabric_issued, String fabric_returned, String fabric_consumed, String balance, String util) {
        this.job_no = job_no;
        this.buyer = buyer;
        this.style = style;
        this.production_qty = production_qty;
        this.fabric = fabric;
        this.color = color;
        this.buyer_po = buyer_po;
        this.order_qty = order_qty;
        Cutting_ord_avg = cutting_ord_avg;
        Actual_prod_avg = actual_prod_avg;
        Actual_cutting = actual_cutting;
        Fabric_issued = fabric_issued;
        Fabric_returned = fabric_returned;
        Fabric_consumed = fabric_consumed;
        Balance = balance;
        this.util = util;
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

    public String getCutting_ord_avg() {
        return Cutting_ord_avg;
    }

    public String getActual_prod_avg() {
        return Actual_prod_avg;
    }

    public String getActual_cutting() {
        return Actual_cutting;
    }

    public String getFabric_issued() {
        return Fabric_issued;
    }

    public String getFabric_returned() {
        return Fabric_returned;
    }

    public String getFabric_consumed() {
        return Fabric_consumed;
    }

    public String getBalance() {
        return Balance;
    }

    public String getUtil() {
        return util;
    }
}
