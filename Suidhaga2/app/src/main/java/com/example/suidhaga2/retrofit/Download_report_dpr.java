package com.example.suidhaga2.retrofit;
import com.google.gson.annotations.SerializedName;
public class Download_report_dpr {

    @SerializedName("response")
    String download_report;

    public Download_report_dpr(String download_report){
        this.download_report=download_report;
    }
    public String getDownload_report_dpr(){return download_report;}
}