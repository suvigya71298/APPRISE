package com.example.suidhaga2.retrofit;

import com.google.gson.annotations.SerializedName;

public class Job_data {


    @SerializedName("job_no")
    String job_no;

    public Job_data(String job_no) {
        this.job_no = job_no;
    }

    public String getJob_no() {
        return job_no;
    }
}
