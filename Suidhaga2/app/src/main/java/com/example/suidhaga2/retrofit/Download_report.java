package com.example.suidhaga2.retrofit;
import com.google.gson.annotations.SerializedName;
public class Download_report {

    @SerializedName("response")
    String download_report;

public Download_report(String download_report){
 this.download_report=download_report;
}
public String getDownload_report(){return download_report;}
}